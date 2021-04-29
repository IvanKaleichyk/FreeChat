package com.kaleichyk.feature_user_info

import android.net.Uri
import com.kaleichyk.core_cloud_storage.framework.CloudStorageRepository
import com.kaleichyk.core_cloud_storage.framework.results.UploadResult
import com.kaleichyk.core_database.api.DialogsRepository
import com.kaleichyk.core_database.api.UsersRepository
import com.kaleichyk.data.CurrentUser
import com.koleychik.core_authentication.api.AccountRepository
import com.koleychik.core_files.FilesRepository
import com.koleychik.models.DeviceImage
import com.koleychik.models.Dialog
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.user.UserResult
import javax.inject.Inject

class UserInfoDataSource @Inject constructor(
    private val usersRepository: UsersRepository,
    private val dialogsRepository: DialogsRepository,
    private val accountRepository: AccountRepository,
    private val cloudStorageRepository: CloudStorageRepository,
    private val filesRepository: FilesRepository
) {

    fun getUser(id: String, res: (UserResult) -> Unit) {
        usersRepository.getUserById(id, res)
    }

    fun deleteUser(id: String, res: (CheckResult) -> Unit) {
        usersRepository.deleteUser(id, res)
    }

    fun createNewDialog(userId: String, dialog: Dialog, res: (CheckResult) -> Unit) {
        dialogsRepository.addDialog(dialog) {
            usersRepository.bindDialogIdToUser(userId, dialog.id, res)
        }
    }

    fun signOut() {
        accountRepository.signOut()
    }

    fun setName(name: String, res: (CheckResult) -> Unit) {
        accountRepository.updateName(name, res)
    }

    fun setEmail(email: String, res: (CheckResult) -> Unit) {
        accountRepository.updateEmail(email, res)
    }

    fun setPassword(password: String, res: (CheckResult) -> Unit) {
        accountRepository.updatePassword(password, res)
    }

    fun setIcon(userId: String, uri: Uri, contextType: String?, res: (CheckResult) -> Unit) {
        cloudStorageRepository.addImage(userId, uri, contextType) {
            when (it) {
                is UploadResult.Successful -> {
                    if (CurrentUser.user?.id == userId) CurrentUser.user?.icon = it.uri
                    accountRepository.updateIcon(it.uri, res)
                }
                is UploadResult.Error -> res(CheckResult.ServerError(it.message))
            }
        }
    }

    fun setBackground(userId: String, uri: Uri, contextType: String?, res: (CheckResult) -> Unit) {
        cloudStorageRepository.addImage(userId, uri, contextType) {
            when (it) {
                is UploadResult.Successful -> {
                    if (CurrentUser.user?.id == userId) CurrentUser.user?.background = it.uri
                    accountRepository.updateBackground(it.uri, res)
                }
                is UploadResult.Error -> res(CheckResult.ServerError(it.message))
            }
        }
    }

    fun getImagesOnDevice(): List<DeviceImage> = filesRepository.getImages()


}