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
public class BillLookupResult {

    @NotNull(message = "ID_REQUIRED")
    private UUID id;

    private String description;

    private Bill fullPayload;

}
