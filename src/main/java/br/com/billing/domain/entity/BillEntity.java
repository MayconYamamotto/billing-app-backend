package br.com.billing.domain.entity;

import br.com.billing.domain.enums.BillingStatus;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ParamDef;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static br.com.billing.utils.MessageUtils.ID_REQUIRED;

@Data
@Entity
@SuperBuilder
@Table(name = "bill")
@NoArgsConstructor
@AllArgsConstructor
@FilterDef(name = "sysuserFilter", parameters = @ParamDef(name = "sysuser", type = UUID.class))
@EqualsAndHashCode(callSuper = true)
public class BillEntity extends BaseEntity {

    @Id
    @NotNull(message = ID_REQUIRED)
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "import_history")
    private BillingImportHistoryEntity importHistory;

    @NotNull(message = "Data vencimento é obrigatório.")
    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @NotBlank(message = "Descrição é obrigatório.")
    @Size(max = 255, message = "Descrição pode ter no máximo 255 caracteres.")
    @Column(name = "description")
    private String description;

    @NotNull(message = "Valor é obrigatório.")
    @Column(name = "amount")
    private BigDecimal amount;

    @Valid
    @NotNull(message = "Usuário é obrigatório.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sysuser")
    private UserEntity sysuser;

    @NotNull(message = "Status é obrigatório.")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BillingStatus status;

}
