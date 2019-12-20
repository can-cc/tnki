package com.tnki.core.memox.model;

import java.util.List;

public interface Memo {
    List<MemoLearningItem> getLearningItem(int userID);
    MemoLearningItem learnItem(MemoItem item, int userID, int memoQuality);
    void startLearnItem(MemoItem item, int userID);
    void finishLearnItem(MemoLearningItem item);
    void fillItemToLearn(int userID);
}
