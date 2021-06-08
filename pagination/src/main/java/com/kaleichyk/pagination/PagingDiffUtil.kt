package com.kaleichyk.pagination

import com.kaleichyk.pagination.models.PaginationData

interface PagingDiffUtil<T : PaginationData> {

    fun compare(o1: T, o2: T): Int

    fun areItemsTheSame(item1: T, item2: T): Boolean

}