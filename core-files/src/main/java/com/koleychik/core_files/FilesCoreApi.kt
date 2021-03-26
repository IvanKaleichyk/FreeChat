package com.koleychik.core_files

import com.koleychik.module_injector.injections.BaseApi

interface FilesCoreApi : BaseApi {
    fun repository(): FilesRepository
}