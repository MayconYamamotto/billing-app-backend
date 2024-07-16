package br.com.billing.domain.service;

import br.com.billing.domain.dto.Login;
import br.com.billing.domain.dto.User;

public interface UserService {

    User login(Login user);
    User signup(User user);
    User findByEmail(String email);

}
