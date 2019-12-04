package com.tnki.core.memox.model;

import com.tnki.core.memox.command.CreateLearnItemCommand;
import org.springframework.stereotype.Component;

@Component
public class MemoItemFactory {

    public MemoItem createMemoItemFromCommand(CreateLearnItemCommand command) {
        MemoItem item = new MemoItem();
        item.setFront(command.getFront());
        item.setTip(command.getTip());
        item.setBack(command.getBack());
        return item;
    }

    public MemoLearningItem createMemoLearningItem(MemoItem memoItem, int userID) {
        return new MemoLearningItem(memoItem, userID);
    }

}
