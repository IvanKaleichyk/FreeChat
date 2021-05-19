package com.kaleichyk.utils

import android.app.Application

class StringConverter private constructor(private val application: Application) {

    companion object {
        private lateinit var instance: StringConverter

        fun init(application: Application) {
            instance = StringConverter(application)
        }

        fun get(): StringConverter = instance
    }

    fun convert(res: Int) = application.getString(res)

}