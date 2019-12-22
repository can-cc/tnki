package com.tnki.core.memox.model;

import java.util.List;

public interface Memo {
    List<MemoLearningItem> getLearningItem(int userID);
    MemoLearningItem learnItem(MemoLearningItem memoLearningItem, int memoQuality);
    void startLearnItem(MemoItem item, int userID);
    void finishLearnItem(MemoLearningItem item);
    void fillItemToLearn(int userID);
}
