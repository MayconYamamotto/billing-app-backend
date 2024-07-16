package br.com.billing.infrastructure.controller;

import br.com.billing.domain.dto.Login;
import br.com.billing.domain.dto.Token;
import br.com.billing.domain.dto.User;
import br.com.billing.domain.service.UserService;
import br.com.billing.infrastructure.auth.UserAuthenticationProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("app/entities/user")
public class UserController {

    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/login")
    public ResponseEntity<Token> login(@Valid @RequestBody Login login) {
        User user = userService.login(login);
        Token token = Token.builder().token(userAuthenticationProvider.createToken(user)).build();
        return ResponseEntity.ok(token);
    }

    @PostMapping("/signup")
    public ResponseEntity<Token> signup(@Valid @RequestBody User user) {
        User newUser = userService.signup(user);
        Token token = Token.builder().token(userAuthenticationProvider.createToken(newUser)).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }
}
