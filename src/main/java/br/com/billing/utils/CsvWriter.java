package br.com.billing.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.StringJoiner;

@Data
@NoArgsConstructor
public class CsvWriter {

    private final StringWriter stringWriter = new StringWriter();
    private final List<String> header = new ArrayList<>();
    private final List<String> rows = new ArrayList<>();
    private PrintWriter printWriter = new PrintWriter(stringWriter);

    public void setHeader(String... headerValues) {
        header.clear();
        header.add(joinValues(headerValues));
    }

    public void writeLine(String... values) {
        rows.add(joinValues(values));
    }

    public String getCsvContentAsBase64() {
        resetPrintWriter();

        if (!header.isEmpty()) {
            printWriter.println(header.get(0));
        }

        for (String row : rows) {
            printWriter.println(row);
        }

        printWriter.flush();
        String csvContent = stringWriter.toString();
        return Base64.getEncoder().encodeToString(csvContent.getBytes(StandardCharsets.UTF_8));
    }

    private String joinValues(String... values) {
        StringJoiner joiner = new StringJoiner(",");
        for (String value : values) {
            joiner.add(value);
        }
        return joiner.toString();
    }

    private void resetPrintWriter() {
        printWriter = new PrintWriter(stringWriter);
    }
}
