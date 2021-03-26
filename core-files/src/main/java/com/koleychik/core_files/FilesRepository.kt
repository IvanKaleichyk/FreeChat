package com.koleychik.core_files

import com.koleychik.models.DeviceImage

interface FilesRepository {
    fun getImages(): List<DeviceImage>
}