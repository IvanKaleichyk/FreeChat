package com.kaleichyk.pagination.models

abstract class PaginationData

object PaginationLoading : PaginationData()

data class PaginationError(val message: String, val code: Int) : PaginationData()