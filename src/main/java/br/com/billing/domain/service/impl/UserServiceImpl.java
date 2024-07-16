package br.com.billing.domain.service.impl;

import br.com.billing.domain.dto.Login;
import br.com.billing.domain.dto.User;
import br.com.billing.domain.entity.UserEntity;
import br.com.billing.domain.repository.UserRepository;
import br.com.billing.domain.service.UserService;
import br.com.billing.domain.exception.BusinessException;
import br.com.billing.utils.ObjectConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ObjectConverter<User, UserEntity> objectConverter;

    @Override
    public User login(Login login) {
        UserEntity userOptional = userRepository.findByEmail(login.getEmail()).orElseThrow(() -> new IllegalStateException("Usuário não encontrado."));

        if (!passwordEncoder.matches(login.getPassword(), userOptional.getPassword())) {
            throw new IllegalStateException("Senha inválida.");
        }

        return objectConverter.convertEntityToDto(userOptional, User.class);
    }

    @Override
    public User signup(User user) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(user.getEmail());

        if (userOptional.isPresent()) {
            throw new IllegalStateException("Usuário já cadastrado.");
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            throw new IllegalStateException("Senhas não conferem.");
        }

        UserEntity userEntity = objectConverter.convertDtoToEntity(user, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity = userRepository.save(userEntity);

        return objectConverter.convertEntityToDto(userEntity, User.class);
    }

    @Override
    public User findByEmail(String email) {
        return objectConverter.convertEntityToDto(userRepository.findByEmail(email).orElseThrow(() -> new BusinessException("Usuário não encontrado.")), User.class);
    }
}
