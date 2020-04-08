package com.tnki.core.auth;

import com.tnki.core.auth.command.LoginCommand;
import com.tnki.core.auth.command.SignUpCommand;
import com.tnki.core.auth.repository.impl.UserRepositoryImpl;
import com.tnki.core.auth.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthApplicationService {
    final private SecurityService securityService;
    final private UserRepositoryImpl userRepository;

    @Autowired
    public AuthApplicationService(SecurityService securityService, UserRepositoryImpl userRepository) {
        this.securityService = securityService;
        this.userRepository = userRepository;
    }

    long login(LoginCommand command) {
        return securityService.autoLogin(command.username, command.password);
    }

    void signUp(SignUpCommand command) {
        securityService.registerNewUser(command);
    }

    public int getIdByUsername(String username) {
        return userRepository.queryUserIdByUsername(username);
    }

}
