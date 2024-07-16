package br.com.billing.domain.service;

import br.com.billing.domain.record.PayInBulkOutput;

public interface BillingImportHistoryService {

    PayInBulkOutput processPayInBulk(byte[] file);

}
