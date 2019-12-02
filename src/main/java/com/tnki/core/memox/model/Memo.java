package com.tnki.core.memox.model;

import java.util.Date;
import java.util.List;

public interface Memo {
    List<MemoItem> getLearningItem(String userID, int limit, Date date);

    MemoLearningItem learnItem(MemoItem item, int userID, int memoQuality);

    void startLearnItem(MemoItem item, int userID);

    void finishLearnItem(MemoLearningItem item);
}
