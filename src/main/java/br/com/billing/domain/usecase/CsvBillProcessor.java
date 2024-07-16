package br.com.billing.domain.usecase;

import br.com.billing.domain.record.PayInBulkOutput;

public interface CsvBillProcessor {

    PayInBulkOutput payInBulk(byte[] file);

}
