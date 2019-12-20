package com.tnki.core.auth;

import com.tnki.core.auth.command.LoginCommand;
import com.tnki.core.auth.command.SignUpCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    private final AuthApplicationService authApplicationService;

    @Autowired
    public AuthController(AuthApplicationService authApplicationService) {
        this.authApplicationService = authApplicationService;
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public void signin(@RequestBody @Valid LoginCommand command) {
        authApplicationService.login(command);
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public void signup(@RequestBody @Valid SignUpCommand command) {
        authApplicationService.signUp(command);
    }
}
