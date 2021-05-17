package com.kaleichyk.feature_user_info.ui.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaleichyk.feature_user_info.UserInfoManager
import com.koleychik.models.Dialog
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.dialog.DialogResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class UserInfoViewModel @Inject constructor(
    private val manager: UserInfoManager
) : ViewModel() {

    private val _setDataRequest = MutableLiveData<CheckResult>(null)
    val setDataRequest: LiveData<CheckResult> get() = _setDataRequest

    private val _deleteUserRequest = MutableLiveData<CheckResult>()
    val deleteUserRequest: LiveData<CheckResult> get() = MutableLiveData()

    private val _createNewDialogRequest = MutableLiveData<DialogResult>(null)
    val createNewDialogRequest: LiveData<DialogResult> get() = _createNewDialogRequest

    fun deleteUser(id: String) = viewModelScope.launch(Dispatchers.IO) {
        _deleteUserRequest.value = manager.deleteUser(id)
    }

    fun createNewDialog(dialog: Dialog) = viewModelScope.launch(Dispatchers.IO) {
        _createNewDialogRequest.value = manager.createNewDialog(dialog)
    }

    fun signOut() {
        manager.signOut()
    }

    fun setName(name: String) = viewModelScope.launch(Dispatchers.IO) {
        _setDataRequest.value = manager.setName(name)
    }

    fun setEmail(email: String) = viewModelScope.launch(Dispatchers.IO) {
        _setDataRequest.value = manager.setEmail(email)
    }

    fun setPassword(password: String) = viewModelScope.launch(Dispatchers.IO) {
        _setDataRequest.value = manager.setPassword(password)
    }

    fun setIcon(userId: String, uri: Uri, contextType: String?) =
        viewModelScope.launch(Dispatchers.IO) {
            _setDataRequest.value = manager.setIcon(userId, uri, contextType)
        }

    fun setBackground(userId: String, uri: Uri, contextType: String?) =
        viewModelScope.launch(Dispatchers.IO) {
            _setDataRequest.value = manager.setBackground(userId, uri, contextType)
        }
}