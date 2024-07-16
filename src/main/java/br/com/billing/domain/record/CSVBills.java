package br.com.billing.domain.record;

import br.com.billing.domain.enums.BillingStatus;

import java.util.UUID;

public record CSVBills(UUID id, String description, BillingStatus status, String messageError) {
}
