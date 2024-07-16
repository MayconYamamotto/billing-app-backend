package br.com.billing.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserAuthenticatedJwt {

    private UUID id;
    private String fullName;
    private String firstName;
    private String lastName;
    private String email;

}
