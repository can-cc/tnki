package com.tnki.core.auth.service;

import com.tnki.core.auth.command.SignUpCommand;
import org.springframework.stereotype.Service;

@Service
public interface SecurityService {
    void registerNewUser(SignUpCommand command);

    int autoLogin(String username, String password);
}