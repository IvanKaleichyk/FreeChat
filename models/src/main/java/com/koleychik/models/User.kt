package com.koleychik.models

import android.net.Uri

data class User(
    val id: String,
    var name: String,
    var email: String,
    var icon: Uri?,
    var background: Uri?
)