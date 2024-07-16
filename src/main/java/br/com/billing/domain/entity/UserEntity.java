package br.com.billing.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

import static br.com.billing.utils.MessageUtils.ID_REQUIRED;

@Data
@Entity
@SuperBuilder
@Table(name = "sysuser")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends BaseEntity {

    @Id
    @NotNull(message = ID_REQUIRED)
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @NotBlank(message = "Primeiro nome é obrigatório.")
    @Size(max = 255, message = "Primeiro nome pode ter no máximo 255 caracteres.")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Último nome é obrigatório.")
    @Size(max = 255, message = "Último nome pode ter no máximo 255 caracteres.")
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "Senha é obrigatório.")
    @Size(max = 255, message = "Senha pode ter no máximo 255 caracteres.")
    @Column(name = "password")
    private String password;

    @Transient
    private String confirmPassword;

    @Size(max = 255, message = "E-mail pode ter no máximo 255 caracteres.")
    @Email(message = "O e-mail informado '${validatedValue}' é inválido.")
    @Column(name = "email")
    private String email;

    @NotNull(message = "Ativo é obrigatório.")
    @Column(name = "active")
    @Builder.Default
    private Boolean active = true;
}

