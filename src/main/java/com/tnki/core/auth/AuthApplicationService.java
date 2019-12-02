package com.tnki.core.auth;

import com.tnki.core.auth.command.LoginCommand;
import com.tnki.core.auth.command.SignUpCommand;
import com.tnki.core.auth.repository.impl.UserRepository;
import com.tnki.core.auth.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthApplicationService {
    final private SecurityService securityService;
    final private UserRepository userRepository;

    @Autowired
    public AuthApplicationService(SecurityService securityService, UserRepository userRepository) {
        this.securityService = securityService;
        this.userRepository = userRepository;
    }

    public void login(LoginCommand command) {
        securityService.autoLogin(command.username, command.password);
    }

    public void signUp(SignUpCommand command) {
        securityService.registerNewUser(command);
    }

    public int getUserIdByUsername(String username) {
        return userRepository.queryUserIdByUsername(username);
    }
}
