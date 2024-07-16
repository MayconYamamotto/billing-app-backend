package br.com.billing.infrastructure.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserAuthenticationProvider userAuthenticationProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        String path = request.getRequestURI();
        String method = request.getMethod();

        if ((HttpMethod.POST.name().equals(method) && ("/app/entities/user/login".equals(path) || "/app/entities/user/signup".equals(path)))) {
            filterChain.doFilter(request, response);
            return;
        }

        if (header != null) {
            String[] authElements = header.split(" ");

            if (authElements.length == 2
                    && "Bearer".equals(authElements[0])) {
                try {
                    if ("GET".equals(request.getMethod())) {
                        SecurityContextHolder.getContext().setAuthentication(
                                userAuthenticationProvider.validateToken(authElements[1]));
                    } else {
                        SecurityContextHolder.getContext().setAuthentication(
                                userAuthenticationProvider.validateTokenStrongly(authElements[1]));
                    }
                } catch (IllegalStateException e) {
                    SecurityContextHolder.clearContext();
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Parece que sua sessão expirou ou suas credenciais estão incorretas. Por favor, faça login novamente para continuar acessando este recurso.");
                    return;
                }
            }
        } else {
            throw new IllegalStateException("Token não encontrado.");
        }

        filterChain.doFilter(request, response);
    }
}
