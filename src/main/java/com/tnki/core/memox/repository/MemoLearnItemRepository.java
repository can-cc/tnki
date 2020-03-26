package com.tnki.core.memox.repository;

import com.tnki.core.memox.model.MemoLearningItem;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MemoLearnItemRepository {
    Optional<Integer> shouldLearnSize(int userID, Date nextLearnDate);

    Optional<Integer> learnedSize(int userID, Date lastLearnDate);

    List<MemoLearningItem> findAll(int userID, Date nextLearnDate);

    List<MemoLearningItem> find(int userID, int offset, int limit);

    MemoLearningItem findOne(int memoItemID, int userID);

    void add(MemoLearningItem memoLearningItem);

    void update(MemoLearningItem memoLearningItem);
}
