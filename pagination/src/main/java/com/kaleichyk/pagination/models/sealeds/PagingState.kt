package com.kaleichyk.pagination.models.sealeds

import com.kaleichyk.pagination.models.PaginationData
import com.kaleichyk.pagination.models.PaginationError

sealed class PagingState {
    object WaitingForLoading : PagingState()
    object Loading : PagingState()
    object End : PagingState()
    class Error(val error: PaginationError) : PagingState()
    class ReturnResult<T : PaginationData>(val data: List<T>) : PagingState()
}

