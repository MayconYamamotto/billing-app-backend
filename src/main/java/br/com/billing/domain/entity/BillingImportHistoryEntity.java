package br.com.billing.domain.entity;

import br.com.billing.domain.enums.ImportStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

import static br.com.billing.utils.MessageUtils.ID_REQUIRED;

@Data
@Entity
@SuperBuilder
@Table(name = "billing_import_history")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BillingImportHistoryEntity extends BaseEntity {

    @Id
    @NotNull(message = ID_REQUIRED)
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @NotNull(message = "Data da importação é obrigatório.")
    @Column(name = "import_date")
    private LocalDateTime importDate;

    @NotBlank(message = "Nome do arquivo é obrigatório.")
    @Size(max = 255, message = "Nome do arquivo pode ter no máximo 255 caracteres.")
    @Column(name = "file_name")
    private String fileName;

    @Column(name = "blob")
    private String blob;

    @NotNull(message = "Status é obrigatório.")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ImportStatus status;
}
