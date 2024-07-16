package br.com.billing.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private UUID id;

    @NotBlank(message="Primeiro nome é obrigatório.")
    @Size(max = 255, message = "Primeiro nome pode ter no máximo 255 caracteres.")
    private String firstName;

    @NotBlank(message="Último nome é obrigatório.")
    @Size(max = 255, message = "Último nome pode ter no máximo 255 caracteres.")
    private String lastName;

    @NotBlank(message="Senha é obrigatório.")
    @Size(max = 255, message = "Senha pode ter no máximo 255 caracteres.")
    private String password;

    @NotBlank(message="Confirmação da senha é obrigatório.")
    @Size(max = 255, message = "Confirmação da senha pode ter no máximo 255 caracteres.")
    private String confirmPassword;

    @Size(max = 255, message = "E-mail pode ter no máximo 255 caracteres.")
    @Email(message="O e-mail informado '${validatedValue}' é inválido.")
    private String email;

    @Builder.Default
    private Boolean active = true;

    @Size(max = 255, message = "Criado por pode ter no máximo 255 caracteres.")
    private String createdBy;

    private LocalDateTime createdDate;

    @Size(max = 255, message = "Alterado por pode ter no máximo 255 caracteres.")
    private String lastModifiedBy;

    private LocalDateTime lastModifiedDate;

    public boolean isActive() {
        return Boolean.TRUE.equals(active);
    }

    @JsonIgnore
    public boolean isNew() {
        return null == id;
    }

    @JsonIgnore
    public boolean isNotNew() {
        return null != id;
    }

    public String sTransform() {
        var text = new ArrayList<String>();
        text.add(!firstName.isEmpty() ? firstName : "");

        return text //
                .stream() //
                .filter(item -> !item.isEmpty()) //
                .collect(Collectors.joining(" - "));
    }
}
