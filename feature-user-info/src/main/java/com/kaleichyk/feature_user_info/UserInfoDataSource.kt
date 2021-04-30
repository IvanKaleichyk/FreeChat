package com.kaleichyk.feature_user_info

import android.net.Uri
import com.kaleichyk.core_cloud_storage.framework.CloudStorageRepository
import com.kaleichyk.core_cloud_storage.framework.results.UploadResult
import com.kaleichyk.core_database.api.DialogsRepository
import com.kaleichyk.core_database.api.UsersRepository
import com.kaleichyk.data.CurrentUser
import com.koleychik.core_authentication.api.AccountRepository
import com.koleychik.models.Dialog
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.dialog.DialogResult
import javax.inject.Inject

internal class UserInfoDataSource @Inject constructor(
    private val usersRepository: UsersRepository,
    private val dialogsRepository: DialogsRepository,
    private val accountRepository: AccountRepository,
    private val cloudStorageRepository: CloudStorageRepository,
) {

    fun deleteUser(id: String, res: (CheckResult) -> Unit) {
        accountRepository.deleteUser()
        usersRepository.deleteUser(id, res)
    }

    fun createNewDialog(
        dialog: Dialog,
        res: (DialogResult) -> Unit
    ) {
        dialogsRepository.addDialog(dialog) { dialogResult ->
            if (dialogResult !is DialogResult.Successful) res(dialogResult)
            else usersRepository.bindDialogIdToUser(dialog.users[0].id, dialog.id) { user1Res ->
                if (user1Res !is CheckResult.Successful) holderCheckResultToDialog(dialog, user1Res, res)
                else usersRepository.bindDialogIdToUser(dialog.users[1].id, dialog.id){ user2Res ->
                    holderCheckResultToDialog(dialog, user2Res, res)
                }
            }
        }
    }

    private fun holderCheckResultToDialog(
        dialog: Dialog,
        value: CheckResult,
        res: (DialogResult) -> Unit
    ) {
        when (value) {
            is CheckResult.Successful -> res(DialogResult.Successful(dialog))
            is CheckResult.DataError -> res(DialogResult.DataError(value.message))
            is CheckResult.ServerError -> res(DialogResult.ServerError(value.message))
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
                    if (CurrentUser.user?.id == userId) CurrentUser.user?.icon = it.uri.toString()
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
                    if (CurrentUser.user?.id == userId) CurrentUser.user?.background =
                        it.uri.toString()
                    accountRepository.updateBackground(it.uri, res)
                }
                is UploadResult.Error -> res(CheckResult.ServerError(it.message))
            }
        }
    }


}