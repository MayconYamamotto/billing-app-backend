package br.com.billing.domain.service.impl;

import br.com.billing.domain.record.PayInBulkOutput;
import br.com.billing.domain.service.BillingImportHistoryService;
import br.com.billing.domain.usecase.CsvBillProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillingImportHistoryServiceImpl implements BillingImportHistoryService {

    private final CsvBillProcessor csvBillProcessor;
    
    @Override
    @Transactional
    public PayInBulkOutput processPayInBulk(byte[] file) {
        return csvBillProcessor.payInBulk(file);
    }
}
