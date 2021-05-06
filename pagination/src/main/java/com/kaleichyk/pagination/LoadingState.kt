package com.kaleichyk.pagination

sealed class LoadingState{
    object Loading: LoadingState()
    object NotLoading: LoadingState()
    object Error: LoadingState()
}
