package com.tnki.core.memox.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MemoItem {
    private String ID;
    private String frontText;
    private String BackText;
    private String tipText;
    private Date createdAt;
    private Date updatedAt;
}
