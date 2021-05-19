package com.kaleichyk.feature_user_info

import android.net.Uri
import com.kaleichyk.core_cloud_storage.framework.CloudStorageRepository
import com.kaleichyk.core_cloud_storage.framework.results.UploadResult
import com.kaleichyk.core_cloud_storage.framework.results.toCheckResult
import com.kaleichyk.core_database.api.DialogsRepository
import com.kaleichyk.core_database.api.UsersRepository
import com.kaleichyk.utils.CurrentUser
import com.koleychik.core_authentication.api.AccountRepository
import com.koleychik.models.Dialog
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.dialog.DialogResult
import com.koleychik.models.results.toDialogResult
import javax.inject.Inject

internal class UserInfoManager @Inject constructor(
    private val usersRepository: UsersRepository,
    private val dialogsRepository: DialogsRepository,
    private val accountRepository: AccountRepository,
    private val cloudStorageRepository: CloudStorageRepository,
) {

    suspend fun deleteUser(id: String): CheckResult {
        val result = accountRepository.deleteUser()
        if (result !is CheckResult.Successful) return result
        return usersRepository.deleteUser(id)
    }

    suspend fun createNewDialog(dialog: Dialog): DialogResult {
        val addDialogresult = dialogsRepository.addDialog(dialog)
        if (addDialogresult !is DialogResult.Successful) return addDialogresult

        // bind dialog id to first user
        var bindDialogIdToUserResult =
            usersRepository.bindDialogIdToUser(dialog.users[0].id, dialog.id)

        if (bindDialogIdToUserResult is CheckResult.Error) return bindDialogIdToUserResult.toDialogResult()

        // bind dialog id to second user
        bindDialogIdToUserResult =
            usersRepository.bindDialogIdToUser(dialog.users[1].id, dialog.id)

        return when (bindDialogIdToUserResult) {
            is CheckResult.Successful -> DialogResult.Successful(dialog)
            is CheckResult.Error -> bindDialogIdToUserResult.toDialogResult()
        }
    }

    fun signOut() {
        accountRepository.signOut()
    }

    suspend fun setName(name: String): CheckResult = accountRepository.updateName(name)


    suspend fun setEmail(email: String) = accountRepository.updateEmail(email)


    suspend fun setPassword(password: String) = accountRepository.updatePassword(password)


    suspend fun setIcon(userId: String, uri: Uri, contextType: String?): CheckResult {

        val uploadImageResult = cloudStorageRepository.addImage(userId, uri, contextType)

        if (uploadImageResult is UploadResult.Successful) {
            val updateIconResult = accountRepository.updateIcon(uploadImageResult.uri)
            return if (updateIconResult is CheckResult.Successful) {
                CurrentUser.user?.icon = uploadImageResult.uri.toString()
                updateIconResult
            } else updateIconResult
        }
        return uploadImageResult.toCheckResult()
    }

    suspend fun setBackground(userId: String, uri: Uri, contextType: String?): CheckResult {
        val uploadImageResult = cloudStorageRepository.addImage(userId, uri, contextType)

        if (uploadImageResult is UploadResult.Successful) {
            val uploadUri = uploadImageResult.uri
            val updateBackgroundResult = accountRepository.updateBackground(uploadUri)
            return if (updateBackgroundResult is CheckResult.Successful) {
                CurrentUser.user?.background = uploadUri.toString()
                updateBackgroundResult
            } else updateBackgroundResult
        }

        return uploadImageResult.toCheckResult()
    }


}