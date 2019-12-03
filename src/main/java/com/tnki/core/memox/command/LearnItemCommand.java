package com.tnki.core.memox.command;

import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Value
public class LearnItemCommand {
    @Valid
    @NotEmpty(message = "itemID cannot be empty")
    private int itemID;

    @Valid
    @NotEmpty(message = "memoQuality cannot be empty")
    private int memoQuality;
}
