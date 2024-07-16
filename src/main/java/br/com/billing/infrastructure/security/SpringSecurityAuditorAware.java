package br.com.billing.infrastructure.security;

import br.com.billing.domain.dto.UserAuthenticatedJwt;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    private static final String ADMIN = "ADMIN";

    @Override
    public Optional<String> getCurrentAuditor() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return Optional.of(ADMIN);
        }

        var principal = authentication.getPrincipal();

        if (principal instanceof UserAuthenticatedJwt auth) {
            return Optional.ofNullable(auth.getFullName());
        }

        return Optional.of(ADMIN);
    }
}