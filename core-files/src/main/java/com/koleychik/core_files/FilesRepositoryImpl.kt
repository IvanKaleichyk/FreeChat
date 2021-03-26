package com.koleychik.core_files

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.koleychik.models.DeviceImage
import javax.inject.Inject

class FilesRepositoryImpl @Inject constructor(private val context: Context): FilesRepository {

    override fun getImages(): List<DeviceImage> {
        val uriExternal: Uri = ImagesStorage.EXTERNAL_CONTENT_URI
        val listRes = mutableListOf<DeviceImage>()
        val sorterOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        val cursor = queryContentResolver(
            uriExternal,
            imagesProjections,
            sortedOrder = sorterOrder
        ) ?: return emptyList()

        var id: Long
        while (cursor.moveToNext()) {
            id = cursor.getLong(0)
            listRes.add(
                DeviceImage(
                    id = id,
                    displayName = cursor.getString(1),
                    uri = Uri.withAppendedPath(uriExternal, id.toString()),
                )
            )
        }
        cursor.close()
        return listRes
    }

    private fun queryContentResolver(
        uri: Uri,
        projections: Array<out String>,
        selection: String? = null,
        selectionArgs: Array<out String>? = null,
        sortedOrder: String? = null
    ) = context.contentResolver.query(
        uri,
        projections,
        selection,
        selectionArgs,
        sortedOrder
    )

}