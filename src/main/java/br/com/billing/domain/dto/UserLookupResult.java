package br.com.billing.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserLookupResult {

    @NotNull(message = "ID_REQUIRED")
    private java.util.UUID id;

    private User fullPayload;

}
