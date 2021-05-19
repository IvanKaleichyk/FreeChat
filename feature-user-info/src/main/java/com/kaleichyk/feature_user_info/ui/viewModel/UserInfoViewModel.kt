package com.kaleichyk.feature_user_info.ui.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaleichyk.feature_user_info.UserInfoManager
import com.koleychik.models.Dialog
import com.koleychik.models.results.dialog.toDataState
import com.koleychik.models.results.toCheckDataState
import com.koleychik.models.states.CheckDataState
import com.koleychik.models.states.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class UserInfoViewModel @Inject constructor(
    private val manager: UserInfoManager
) : ViewModel() {

    private val _setDataState = MutableLiveData<CheckDataState>(null)
    val setDataState: LiveData<CheckDataState> get() = _setDataState

    private val _deleteUserState = MutableLiveData<CheckDataState>()
    val deleteUserRequest: LiveData<CheckDataState> get() = _deleteUserState

    private val _createNewDialogState = MutableLiveData<DataState>(null)
    val createNewDialogState: LiveData<DataState> get() = _createNewDialogState

    fun deleteUser(id: String) = viewModelScope.launch(Dispatchers.IO) {
        _deleteUserState.value = manager.deleteUser(id).toCheckDataState()
    }

    fun createNewDialog(dialog: Dialog) = viewModelScope.launch(Dispatchers.IO) {
        _createNewDialogState.value = manager.createNewDialog(dialog).toDataState()
    }

    fun signOut() {
        manager.signOut()
    }

    fun setName(name: String) = viewModelScope.launch(Dispatchers.IO) {
        val result = manager.setName(name).toCheckDataState()
        withContext(Dispatchers.Main) {
            _setDataState.value = result
        }
    }

    fun setEmail(email: String) = viewModelScope.launch(Dispatchers.IO) {
        val result = manager.setEmail(email).toCheckDataState()
        withContext(Dispatchers.Main) {
            _setDataState.value = result
        }
    }

    fun setPassword(password: String) = viewModelScope.launch(Dispatchers.IO) {
        val result = manager.setPassword(password).toCheckDataState()
        withContext(Dispatchers.Main) {
            _setDataState.value = result
        }
    }

    fun setIcon(userId: String, uri: Uri, contextType: String?) =
        viewModelScope.launch(Dispatchers.IO) {
            val result = manager.setIcon(userId, uri, contextType).toCheckDataState()
            withContext(Dispatchers.Main) {
                _setDataState.value = result
            }
        }

    fun setBackground(userId: String, uri: Uri, contextType: String?) =
        viewModelScope.launch(Dispatchers.IO) {
            val result = manager.setBackground(userId, uri, contextType).toCheckDataState()
            withContext(Dispatchers.Main) {
                _setDataState.value = result
            }
        }
}