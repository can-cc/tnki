package com.tnki.core.memox.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Value
public class LearnItemCommand {
    @Valid
    @NotNull(message = "itemID cannot be empty")
    @JsonProperty("itemID")
    private int itemID;

    @Valid
    @NotNull(message = "memoQuality cannot be empty")
    @JsonProperty("memoQuality")
    private int memoQuality;
}
