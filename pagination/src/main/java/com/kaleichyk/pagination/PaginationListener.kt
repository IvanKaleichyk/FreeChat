package com.kaleichyk.pagination

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kaleichyk.pagination.models.sealeds.LoadResult
import com.kaleichyk.pagination.models.sealeds.PagingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class PaginationListener(
    private val layoutManager: LinearLayoutManager,
    private val lifecycleScope: LifecycleCoroutineScope,
    private val pageSize: Int
) : RecyclerView.OnScrollListener() {

    private var currentPage = 1

    internal val state = MutableStateFlow<PagingState>(PagingState.WaitingForLoading)

    fun loadFirstData() {
        showLog("loadFirstData")
        if (layoutManager.itemCount == 0) {
            lifecycleScope.launch {
                state.value = PagingState.Loading
                state.value = load(1).toPagingState()
                currentPage = 2
            }
        }
    }

    abstract suspend fun load(page: Int): LoadResult

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (state.value is PagingState.Loading) return
        if (
            (visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0
        ) {
            lifecycleScope.launch(Dispatchers.IO) {
                state.value = PagingState.Loading
                currentPage = getNewCurrentPage(totalItemCount)
                state.value = load(currentPage).toPagingState()
            }
        }
    }

    private fun getNewCurrentPage(totalItemCount: Int): Int {
        return (totalItemCount / pageSize) + 1
    }

}