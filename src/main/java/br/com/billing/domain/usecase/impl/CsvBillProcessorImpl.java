package br.com.billing.domain.usecase.impl;


import br.com.billing.domain.dto.Bill;
import br.com.billing.domain.record.CSVBills;
import br.com.billing.domain.record.PayInBulkOutput;
import br.com.billing.domain.service.BillService;
import br.com.billing.domain.usecase.CsvBillProcessor;
import br.com.billing.utils.CsvWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class CsvBillProcessorImpl implements CsvBillProcessor {

    private final BillService billService;

    public PayInBulkOutput payInBulk(byte[] file) {
        List<CSVBills> feedback = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(file), StandardCharsets.UTF_8))) {
            List<Bill> bills = readBillsFromCsv(reader, feedback);

            processBills(bills, feedback);
        } catch (IOException e) {
            log.error("Error processing file", e);
        }

        return buildBillsFeedback(feedback);
    }

    private List<Bill> readBillsFromCsv(BufferedReader reader, List<CSVBills> feedback) throws IOException {
        List<Bill> bills = new ArrayList<>();
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.startsWith("id")) {
                continue;
            }

            String[] columns = line.split(",");
            UUID id = UUID.fromString(columns[0]);
            Bill bill = billService.readAsDto(id);

            if (bill == null) {
                feedback.add(new CSVBills(id, null, null, "Conta não encontrada."));
            } else {
                bills.add(bill);
            }
        }

        return bills;
    }

    private void processBills(List<Bill> bills, List<CSVBills> feedback) {
        for (Bill bill : bills) {
            try {
                var billPaid = billService.pay(bill.getId());
                feedback.add(new CSVBills(billPaid.getId(), billPaid.getDescription(), billPaid.getStatus(), "Conta paga com sucesso."));
            } catch (Exception e) {
                feedback.add(new CSVBills(bill.getId(), bill.getDescription(), bill.getStatus(), e.getMessage()));
            }
        }
    }

    private PayInBulkOutput buildBillsFeedback(List<CSVBills> feedback) {
        CsvWriter writer = new CsvWriter();
        writer.setHeader("id", "conta", "status", "mensagem de retorno");

        for (CSVBills line : feedback) {
            writer.writeLine(line.id().toString(), isNull(line.description()) ? "" : line.description(), isNull(line.status()) ? "" : line.status().getLabel(), line.messageError());
        }

        return PayInBulkOutput.builder()
                .base64File(writer.getCsvContentAsBase64())
                .build();
    }

}
