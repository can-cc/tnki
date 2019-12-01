package com.tnki.core.memox.model;

import com.tnki.core.memox.command.CreateLearnItemCommand;
import org.springframework.stereotype.Component;

@Component
public class MemoItemFactory {

    public MemoItem create(CreateLearnItemCommand command) {
        MemoItem item = new MemoItem();
        item.setFront(command.getFront());
        item.setTip(command.getTip());
        item.setBack(command.getBack());
        return item;
    }
}
