package br.com.billing.domain.dto;

import br.com.billing.domain.enums.ImportStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillingImportHistory {

    private UUID id;

    @NotNull(message = "Data da importação é obrigatório.")
    private LocalDateTime importDate;

    @NotBlank(message = "Nome do arquivo é obrigatório.")
    @Size(max = 255, message = "Nome do arquivo pode ter no máximo 255 caracteres.")
    private String fileName;

    private String blob;

    @Builder.Default
    @NotNull(message = "Status é obrigatório.")
    private ImportStatus status = ImportStatus.AWAITING_PROCESSING;

    @Valid
    @NotNull(message = "Usuário é obrigatório.")
    private UserLookupResult sysuser;

    @Size(max = 255, message = "Criado por pode ter no máximo 255 caracteres.")
    private String createdBy;

    private LocalDateTime createdDate;

    @Size(max = 255, message = "Alterado por pode ter no máximo 255 caracteres.")
    private String lastModifiedBy;

    private LocalDateTime lastModifiedDate;

}
