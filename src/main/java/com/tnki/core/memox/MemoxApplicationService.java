package com.tnki.core.memox;

import com.tnki.core.memox.command.CreateLearnItemCommand;
import com.tnki.core.memox.model.MemoItem;
import com.tnki.core.memox.model.MemoItemFactory;
import com.tnki.core.memox.repository.MemoItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MemoxApplicationService {

    @Autowired
    MemoItemFactory memoItemFactory;

    @Autowired
    MemoItemRepository memoItemRepository;

    public int createLearnItem(CreateLearnItemCommand command) {
        MemoItem item = memoItemFactory.create(command);
        memoItemRepository.inertItem(item);
        log.info("Created learn item [{}].", item.getID());
        return item.getID();
    }

}
