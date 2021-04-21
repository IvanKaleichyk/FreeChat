package com.koleychik.feature_sign.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koleychik.core_authentication.api.AuthRepository
import com.koleychik.core_authentication.checkEmail
import com.koleychik.core_authentication.checkName
import com.koleychik.core_authentication.checkPassword
import com.koleychik.core_authentication.result.user.UserResult
import com.koleychik.models.results.CheckResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) :
    ViewModel() {

    val isLoading = MutableLiveData(false)
    val userResult = MutableLiveData<UserResult>(null)
    val checkResult = MutableLiveData<CheckResult>(null)

    fun startCreateAccount(
        name: String,
        email: String,
        password: String,
        repeatPassword: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        withContext(Dispatchers.Main) {
            isLoading.value = true
        }
        val listResult = listOf(
            checkName(name),
            checkEmail(email),
            checkPassword(password, repeatPassword)
        )

        for (i in listResult) {
            if (i !is CheckResult.Successful) {
                withContext(Dispatchers.Main) {
                    isLoading.value = false
                    checkResult.value = i
                }
                return@launch
            }
        }

        authRepository.createAccount(name, email, password) {
            viewModelScope.launch(Dispatchers.Main) {
                userResult.value = it
                isLoading.value = false
            }
        }

    }
}