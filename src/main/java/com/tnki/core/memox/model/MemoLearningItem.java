package com.tnki.core.memox.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MemoLearningItem {
    private int learnTime;
    private double EF;
    private Date nextLearnDate;
    private MemoItem memoItem;


    MemoLearningItem(MemoItem item, int username) {
        this.setMemoItem(item);
    }
}
