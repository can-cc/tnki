package com.tnki.core.memox.repository;

import com.tnki.core.memox.model.MemoLearningItem;

import java.util.Date;
import java.util.List;

public interface MemoLearnItemRepository {
    int shouldLearnSize(int userID, Date nextLearnDate);

    int learnedSize(int userID, Date lastLearnDate);

    List<MemoLearningItem> findAll(int userID, Date nextLearnDate);

    MemoLearningItem findOne(int memoItemID, int userID);

    void add(MemoLearningItem memoLearningItem);

    void update(MemoLearningItem memoLearningItem);
}
