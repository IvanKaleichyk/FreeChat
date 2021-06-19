package com.kaleichyk.pagination

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.kaleichyk.pagination.models.PaginationData
import com.kaleichyk.pagination.models.PaginationError
import com.kaleichyk.pagination.models.PaginationLoading
import com.kaleichyk.pagination.models.sealeds.PagingState
import com.kaleichyk.pagination.viewHolders.ErrorViewHolder
import com.kaleichyk.pagination.viewHolders.LoadingViewHolder
import com.kaleichyk.pagination.viewHolders.ShowDataViewHolder
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class PagingAdapter<T : PaginationData>(
    private val paginationListener: PaginationListener,
    private val lifecycle: Lifecycle,
    private val onAddNewItem: (addToList: List<T>) -> Unit,
    private val diffUtil: PagingDiffUtil<T>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val LOADING_TYPE = 100
        const val ERROR_TYPE = 0
        const val SHOW_DATA_TYPE = 200
    }

    var reverseLayout = false

    private var loadingPosition: Int? = null

    @Suppress("UNCHECKED_CAST")
    private val list by lazy {
        SortedList(
            PaginationData::class.java,
            object : SortedListAdapterCallback<PaginationData>(this) {
                override fun compare(o1: PaginationData, o2: PaginationData): Int {
                    return when {
                        o1 is PaginationLoading || o2 is PaginationListener -> if (reverseLayout) -1 else 1
                        o2 is PaginationLoading -> if (reverseLayout) 1 else -1
                        else -> diffUtil.compare(o1 as T, o2 as T)
                    }
                }

                override fun areContentsTheSame(
                    oldItem: PaginationData,
                    newItem: PaginationData
                ): Boolean = oldItem == newItem

                override fun areItemsTheSame(
                    item1: PaginationData,
                    item2: PaginationData
                ): Boolean {
                    if (item1.javaClass != item2.javaClass) return false
                    if (item1 is PaginationError) return true
                    return diffUtil.areItemsTheSame(item1 as T, item2 as T)
                }
            })
    }

    init {
        subscribe()
    }

    override fun getItemCount(): Int = list.size()

    abstract fun createShowDataViewHolder(parent: ViewGroup, viewType: Int): ShowDataViewHolder<T>

    abstract fun createLoadingViewHolder(parent: ViewGroup): LoadingViewHolder

    abstract fun createErrorViewHolder(parent: ViewGroup): ErrorViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            LOADING_TYPE -> createLoadingViewHolder(parent)
            ERROR_TYPE -> createErrorViewHolder(parent)
            else -> createShowDataViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LoadingViewHolder -> return
            is ErrorViewHolder -> holder.bind(list[position] as PaginationError)
            else -> {
                @Suppress("UNCHECKED_CAST")
                (holder as ShowDataViewHolder<T>).bind(list[position] as T)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is PaginationLoading -> LOADING_TYPE
            is PaginationError -> ERROR_TYPE
            else -> SHOW_DATA_TYPE
        }
    }

    fun submitList(newList: List<T>) {
        list.clear()
        list.addAll(newList)
    }

    @Suppress("UNCHECKED_CAST")
    fun getItem(position: Int): T = list[position] as T?
        ?: throw NullPointerException("adapter haven't item with position = $position")

    private fun subscribe() {
        lifecycle.coroutineScope.launch {
            paginationListener.state
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    when (it) {
                        is PagingState.ReturnResult<*> -> {
                            removeLoading()
                            showLog("PagingState.ReturnResult: size = ${it.data.size}, ${it.data}")
                            @Suppress("UNCHECKED_CAST")
                            onAddNewItem(it.data as List<T>)
                            addToList(it.data)
                        }
                        is PagingState.Error -> {
                            showLog("PagingState.Error, $it")
                            showError(it.error)
                        }
                        is PagingState.Loading -> {
                            showLog("PagingState.Loading")
                            addLoading()
                        }
                        is PagingState.WaitingForLoading -> showLog("PagingState.WaitingForLoading")
                    }
                }
        }
    }

    fun addToList(addList: List<T>) {
        list.addAll(addList)
    }

    fun addItem(item: T) {
        list.add(item)
    }

    private fun addLoading() {
        loadingPosition = list.size()
        list.add(PaginationLoading)
    }

    private fun removeLoading() {
        if (loadingPosition != null) list.removeItemAt(loadingPosition!!)
        loadingPosition = null
    }

    private fun showError(error: PaginationError) {
        list.add(error)
    }

}