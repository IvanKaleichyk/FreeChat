package com.kaleichyk.feature_messages.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kaleichyk.feature_messages.MessagesManager
import com.koleychik.models.Message
import com.koleychik.models.results.MessagesResult

class MessagesPagingSource(
    private val manager: MessagesManager,
    private val dialogId: Long
) : PagingSource<Int, Message>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Message> {
        val page = params.key ?: 1
        val result = manager.getMessages(dialogId, page)

        return when (result) {
            is MessagesResult.Error -> LoadResult.Error(MessagesPagingError(result.message))
            is MessagesResult.Successful -> LoadResult.Page(
                result.list,
                if (page > 0) page - 1 else null,
                if (result.list.isNotEmpty()) page + 1 else null
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Message>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }
}