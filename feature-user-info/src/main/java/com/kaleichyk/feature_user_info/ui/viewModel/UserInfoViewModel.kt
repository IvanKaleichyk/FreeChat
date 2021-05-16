package com.kaleichyk.feature_user_info.ui.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kaleichyk.feature_user_info.UserInfoManager
import com.koleychik.models.Dialog
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.dialog.DialogResult
import javax.inject.Inject

internal class UserInfoViewModel @Inject constructor(
    private val manager: UserInfoManager
) : ViewModel() {

    private val _setDataRequest = MutableLiveData<CheckResult>(null)
    val setDataRequest: LiveData<CheckResult> get() = _setDataRequest

    private val _deleteUserRequest = MutableLiveData<CheckResult>()
    val deleteUserRequest: LiveData<CheckResult> get() = MutableLiveData<CheckResult>()

    private val _createNewDialogRequest = MutableLiveData<DialogResult>(null)
    val createNewDialogRequest: LiveData<DialogResult> get() = _createNewDialogRequest

    fun deleteUser(id: String) {
        manager.deleteUser(id) {
            _deleteUserRequest.value = it
        }
    }

    fun createNewDialog(dialog: Dialog) {
        manager.createNewDialog(dialog) {
            _createNewDialogRequest.value = it
        }
    }

    fun signOut() {
        manager.signOut()
    }

    fun setName(name: String) {
        manager.setName(name) {
            _setDataRequest.value = it
        }
    }

    fun setEmail(email: String) {
        manager.setEmail(email) {
            _setDataRequest.value = it
        }
    }

    fun setPassword(password: String) {
        manager.setPassword(password) {
            _setDataRequest.value = it
        }
    }

    fun setIcon(userId: String, uri: Uri, contextType: String?) {
        manager.setIcon(userId, uri, contextType) {
            _setDataRequest.value = it
        }
    }

    fun setBackground(userId: String, uri: Uri, contextType: String?) {
        manager.setBackground(userId, uri, contextType) {
            _setDataRequest.value = it
        }
    }
}