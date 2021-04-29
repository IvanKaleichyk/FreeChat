package com.kaleichyk.feature_user_info

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koleychik.models.DeviceImage
import com.koleychik.models.Dialog
import com.koleychik.models.results.CheckResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserInfoViewModel @Inject constructor(
    private val dataSource: UserInfoDataSource
) : ViewModel() {

    private val _serverRequest = MutableLiveData<CheckResult>(null)
    val serverRequest: LiveData<CheckResult> get() = _serverRequest

    private val _listImages = MutableLiveData<List<DeviceImage>>(null)
    val listImages: LiveData<List<DeviceImage>> get() = _listImages

    fun deleteUser(id: String) {
        dataSource.deleteUser(id) {
            _serverRequest.value = it
        }
    }

    fun createNewDialog(userId: String, dialog: Dialog) {
        dataSource.createNewDialog(userId, dialog) {
            _serverRequest.value = it
        }
    }

    fun signOut() {
        dataSource.signOut()
    }

    fun setName(name: String) {
        dataSource.setName(name) {
            _serverRequest.value = it
        }
    }

    fun setEmail(email: String) {
        dataSource.setEmail(email) {
            _serverRequest.value = it
        }
    }

    fun setPassword(password: String) {
        dataSource.setPassword(password) {
            _serverRequest.value = it
        }
    }

    fun setIcon(userId: String, uri: Uri, contextType: String?) {
        dataSource.setIcon(userId, uri, contextType) {
            _serverRequest.value = it
        }
    }

    fun setBackground(userId: String, uri: Uri, contextType: String?) {
        dataSource.setBackground(userId, uri, contextType) {
            _serverRequest.value = it
        }
    }

    fun getDeviceImages() = viewModelScope.launch(Dispatchers.IO) {
        val list = dataSource.getImagesOnDevice()
        withContext(Dispatchers.Main) {
            _listImages.value = list
        }
    }

}