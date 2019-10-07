package com.tnki.core.auth.command;

import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Value
public class LoginCommand {
    @Valid
    @NotEmpty(message = "username cannot be empty")
    public String username;

    @Valid
    @NotEmpty(message = "password cannot be empty")
    public String password;
}
