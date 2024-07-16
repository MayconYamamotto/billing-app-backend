package br.com.billing.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Login {

    @NotBlank(message = "Email é obrigatório.")
    @Email(message="O e-mail informado '${validatedValue}' é inválido.")
    private String email;

    @NotBlank(message = "Senha é obrigatório.")
    private String password;

}
