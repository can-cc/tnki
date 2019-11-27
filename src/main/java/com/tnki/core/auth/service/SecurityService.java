package com.tnki.core.auth.service;

import com.tnki.core.auth.command.SignUpCommand;
import com.tnki.core.auth.model.User;
import org.springframework.stereotype.Service;

@Service
public interface SecurityService {
    String findLoggedInUsername();

    User registerNewUser(SignUpCommand command);

    void autoLogin(String username, String password);
}