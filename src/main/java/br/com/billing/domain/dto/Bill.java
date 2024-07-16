package br.com.billing.domain.dto;

import br.com.billing.domain.enums.BillingStatus;
import br.com.billing.domain.strategy.BillStategy;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class Bill {

    private UUID id;

    @Valid
    private BillingImportHistoryLookupResult importHistory;

    @NotNull(message = "Data vencimento é obrigatório.")
    private LocalDate expirationDate;

    private LocalDate paymentDate;

    @NotBlank(message = "Descrição é obrigatório.")
    @Size(max = 255, message = "Descrição pode ter no máximo 255 caracteres.")
    private String description;

    @NotNull(message = "Valor é obrigatório.")
    private BigDecimal amount;

    @Valid
    private UserLookupResult sysuser;

    @NotNull(message = "Status é obrigatório.")
    private BillingStatus status;

    @Size(max = 255, message = "Criado por pode ter no máximo 255 caracteres.")
    private String createdBy;

    private LocalDateTime createdDate;

    @Size(max = 255, message = "Alterado por pode ter no máximo 255 caracteres.")
    private String lastModifiedBy;

    private LocalDateTime lastModifiedDate;

    public Bill pay() {
        BillStategy billStategy = new BillStategy();
        billStategy.isAllowedPayment(this);

        this.status = BillingStatus.PAID;
        this.paymentDate = LocalDate.now();

        return this;
    }

}
