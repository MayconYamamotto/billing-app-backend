package br.com.billing.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillingImportHistoryLookupResult {

    @NotNull(message = "ID_REQUIRED")
    private UUID id;

    private String fileName;

    private BillingImportHistory fullPayload;

}
