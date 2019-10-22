package com.tnki.core.auth;

import com.tnki.core.auth.command.LoginCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    @Autowired
    private AuthApplicationService authApplicationService;

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public String signin(@RequestBody @Valid LoginCommand command) {
        authApplicationService.login(command);
        return "OK";
    }
}
