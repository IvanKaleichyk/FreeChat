package com.kaleichyk.feature_messages.pagination

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaleichyk.feature_messages.MessagesManager
import com.kaleichyk.feature_messages.toMessagesData
import com.kaleichyk.pagination.PaginationListener
import com.kaleichyk.pagination.models.PaginationError
import com.kaleichyk.pagination.models.sealeds.LoadResult
import com.koleychik.models.results.MessagesResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class MessagesPaginationListener @AssistedInject constructor(
    private val manager: MessagesManager,
    @Assisted private val dialogId: Long,
    @Assisted layoutManager: LinearLayoutManager,
    @Assisted coroutineScope: LifecycleCoroutineScope
) : PaginationListener(layoutManager, coroutineScope, PAGE_SIZE) {

    companion object {
        const val PAGE_SIZE = 50
    }

    override suspend fun load(page: Int): LoadResult {
        val result = manager.getMessages(dialogId, page)
        return when (result) {
            is MessagesResult.Successful -> LoadResult.SUCCESSFUL(result.list.toMessagesData())
            is MessagesResult.Error -> LoadResult.ERROR(PaginationError(result.message))
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            dialogId: Long, layoutManager: LinearLayoutManager,
            coroutineScope: LifecycleCoroutineScope
        ): MessagesPaginationListener
    }

}