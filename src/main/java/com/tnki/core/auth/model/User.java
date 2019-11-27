package com.tnki.core.auth.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private int ID;
    private String username;
    private String passwordHash;
}