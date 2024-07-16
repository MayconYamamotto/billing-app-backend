package br.com.billing.domain.record;

import lombok.Builder;

@Builder
public record PayInBulkInput(byte[] file) {
}
