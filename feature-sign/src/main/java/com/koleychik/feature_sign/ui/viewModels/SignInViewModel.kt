package com.koleychik.feature_sign.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koleychik.core_authentication.api.AuthRepository
import com.koleychik.core_authentication.checkEmail
import com.koleychik.core_authentication.checkPassword
import com.koleychik.core_authentication.result.CheckResult
import com.koleychik.core_authentication.result.user.UserResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val isLoading = MutableLiveData(false)
    val userResult = MutableLiveData<UserResult>(null)
    val checkResult = MutableLiveData<CheckResult>(null)

    fun login(email: String, password: String, repeatPassword: String) =
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                isLoading.value = true
            }
            val listAuditResults =
                listOf(checkEmail(email), checkPassword(password, repeatPassword))
            for (i in listAuditResults) if (i !is CheckResult.Successful) {
                withContext(Dispatchers.Main) {
                    checkResult.value = i
                    isLoading.value = false
                }
                return@launch
            }
            authRepository.login(email, password) {
                viewModelScope.launch(Dispatchers.Main) {
                    userResult.value = it
                    isLoading.value = false
                }
            }
        }
}