package com.iterahub.teratour.comparators;

import com.iterahub.teratour.models.MessagesModel;

import java.util.Comparator;

public class MessagesComparator  implements Comparator<MessagesModel> {
    @Override
    public int compare(MessagesModel msg1, MessagesModel msg2) {
        if(msg1.getId() == null || msg2.getId() == null)
            return 0;
        return msg1.getId().compareTo(msg2.getId());
    }
}
