package com.tnki.core.memox.repository;

import com.tnki.core.memox.model.MemoLearningItem;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MemoLearnItemRepository {
    Optional<Integer> shouldLearnSize(long userID, Date nextLearnDate);

    Optional<Integer> learnedSize(long userID, Date lastLearnDate);

    List<MemoLearningItem> findAll(long userID, Date nextLearnDate);

    List<MemoLearningItem> find(long userID, int offset, int limit);

    MemoLearningItem findOne(int memoItemID, long userID);

    void add(MemoLearningItem memoLearningItem);

    void update(MemoLearningItem memoLearningItem);
}
