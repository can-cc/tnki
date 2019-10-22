package com.tnki.core.memox.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MemoLearningItem extends MemoItem {
    private int learnTime;
    private double EF;
    private Date nextLearnDate;

    MemoLearningItem() {}

    MemoLearningItem(MemoItem item) {
        super.setID(item.getID());
        super.setFront(item.getFront());
        super.setTip(item.getTip());
        super.setBack((item.getBack()));
        super.setCreatedAt(item.getCreatedAt());
        super.setUpdatedAt(item.getUpdatedAt());
    }
}
