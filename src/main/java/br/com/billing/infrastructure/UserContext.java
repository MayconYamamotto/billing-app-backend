package br.com.billing.infrastructure;

import br.com.billing.domain.dto.UserAuthenticatedJwt;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Data
@Component
@RequiredArgsConstructor
public class UserContext {

    public static UserAuthenticatedJwt getUser() {
        return (UserAuthenticatedJwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static boolean isLoggedUser(UUID userId) {
        return !UserContext.getUser().getId().equals(userId);
    }
}
