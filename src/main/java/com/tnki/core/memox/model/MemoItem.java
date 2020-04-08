package com.tnki.core.memox.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MemoItem {
    private long ID;
    private String front;
    private String Back;
    private String tip;
    private Date createdAt;
    private Date updatedAt;
}
