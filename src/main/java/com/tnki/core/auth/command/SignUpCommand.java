package com.tnki.core.auth.command;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

public class SignUpCommand {
    @Valid
    @NotEmpty(message = "username cannot be empty")
    public String username;

    @Valid
    @NotEmpty(message = "password cannot be empty")
    public String password;
}
