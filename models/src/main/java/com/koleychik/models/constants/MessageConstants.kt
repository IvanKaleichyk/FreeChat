package com.koleychik.models.constants

object MessageConstants {

    fun getRootPath(dialogId: String) = ROOT_PATH + "_" + dialogId

    const val ID = "id"
    const val DIALOG_ID = "dialogId"
    const val AUTHOR_ID = "authorId"
    const val TEXT = "text"
    const val CREATED_AT = "createdAt"
    const val IMAGE = "image"
    const val IS_READ = "isRead"

    private const val ROOT_PATH = "Messages"
    const val LIMIT = 50
}