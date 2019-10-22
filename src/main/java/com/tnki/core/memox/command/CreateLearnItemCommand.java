package com.tnki.core.memox.command;

import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Value
public class CreateLearnItemCommand {
    @Valid
    @NotEmpty(message = "Front cannot be empty")
    private String front;

    @Valid
    @NotEmpty(message = "Back cannot be empty")
    private String back;

    @Valid
    private String tip;
}
