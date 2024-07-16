package br.com.billing.domain.record;

import lombok.Builder;

@Builder
public record PayInBulkOutput(String base64File) {
}
