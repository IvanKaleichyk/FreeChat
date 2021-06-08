package com.kaleichyk.pagination.models.sealeds

import com.kaleichyk.pagination.models.PaginationData
import com.kaleichyk.pagination.models.PaginationError

sealed class LoadResult {
    class SUCCESSFUL<T : PaginationData>(val data: List<T>) : LoadResult()
    class ERROR(val error: PaginationError) : LoadResult()

    fun toPagingState(): PagingState {
        return when (this) {
            is SUCCESSFUL<*> -> PagingState.ReturnResult(data)
            is ERROR -> PagingState.Error(error)
        }
    }
}