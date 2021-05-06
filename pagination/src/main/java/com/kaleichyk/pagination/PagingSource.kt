package com.kaleichyk.pagination

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class PagingSource<M, MResult> {

    abstract val pageSize : Int

    private val interval: Int by lazy {
        pageSize - (pageSize / 100)
    }

    var startLoadNew = 0
    private set


    private val _state = MutableStateFlow<LoadingState>(LoadingState.Loading)
    val state get() = _state.asStateFlow()

    open suspend fun load(lastPage: Int?, res: (MResult) -> Unit){
        startLoadNew += interval
    }

}