package com.tnki.core.auth.service.impl;

import com.tnki.core.auth.command.SignUpCommand;
import com.tnki.core.auth.exception.UserAlreadyExistException;
import com.tnki.core.auth.model.User;
import com.tnki.core.auth.repository.UserRepository;
import com.tnki.core.auth.service.SecurityService;
import com.tnki.core.memox.MemoxApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SecurityServiceImpl implements SecurityService {
    private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemoxApplicationService memoxApplicationService;

    @Autowired
    public SecurityServiceImpl(
            @Qualifier("TnkiUserDetailsService") UserDetailsService userDetailsService,
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            @Qualifier("PasswordEncoder") PasswordEncoder passwordEncoder,
            MemoxApplicationService memoxApplicationService
    ) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.memoxApplicationService = memoxApplicationService;
    }

    private boolean userExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Transactional
    @Override
    public void registerNewUser(SignUpCommand command) {
        if (userExists(command.username)) {
            throw new UserAlreadyExistException(command.username);
        }

        final User user = new User();
        user.setUsername(command.username);
        user.setPasswordHash(passwordEncoder.encode(command.password));
        userRepository.insertUser(user);

        memoxApplicationService.initUserLearnSetting(user.getID());

    }

    @Override
    public void autoLogin(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            logger.debug(String.format("Auto login %s successfully!", username));
        }
    }
}
