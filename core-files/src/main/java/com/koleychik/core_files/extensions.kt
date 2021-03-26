package com.koleychik.core_files

import android.provider.MediaStore

typealias ImagesStorage = MediaStore.Images.Media

val imagesProjections = arrayOf(
    ImagesStorage._ID,
    ImagesStorage.DISPLAY_NAME
)