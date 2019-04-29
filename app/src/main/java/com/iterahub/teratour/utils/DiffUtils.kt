package com.iterahub.teratour.utils

import com.iterahub.teratour.comparators.MessagesComparator
import com.iterahub.teratour.models.MessagesModel
import java.util.*

class DiffUtils {

    companion object {
        @JvmStatic
        fun filterOutDuplicatesMsg(messagesModelList: MutableList<MessagesModel>): List<MessagesModel> {
            val messagesModels = TreeSet(MessagesComparator())
            messagesModels.addAll(messagesModelList)
            messagesModelList.clear()
            messagesModelList.addAll(messagesModels)
            messagesModelList.sortBy {DateTimeUtils.getDateFromString(it.createdAt)}
            return messagesModelList
        }
    }

}