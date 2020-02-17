package com.tnki.core.memox.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@JsonIgnoreProperties(value = {"userID"})
@Getter
@Setter
public class MemoLearningItem {
    private int userID;
    private int learnTime;
    private double EF;
    private Date nextLearnDate;
    private Date lastLearnDate;
    private MemoItem memoItem;
    private boolean isLearning = true;

    MemoLearningItem(MemoItem item, int userID) {
        this.setMemoItem(item);
        this.setUserID(userID);
    }
}
