package com.koleychik.feature_sign.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koleychik.core_authentication.api.AuthRepository
import com.koleychik.core_authentication.checkEmail
import com.koleychik.core_authentication.checkPassword
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.user.UserResult
import com.koleychik.models.states.CheckDataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _loginState = MutableLiveData<CheckDataState>(null)
    val loginState: LiveData<CheckDataState> get() = _loginState

    fun login(email: String, password: String, repeatPassword: String) =
        viewModelScope.launch(Dispatchers.IO) {

            withContext(Dispatchers.Main) {
                _loginState.value = CheckDataState.Successful
            }

            val listAuditResults =
                listOf(checkEmail(email), checkPassword(password, repeatPassword))
            for (i in listAuditResults) if (i is CheckResult.Error) {

                withContext(Dispatchers.Main) {
                    _loginState.value = i.toCheckDataState()
                }

                return@launch
            }

            val result = authRepository.login(email, password).toCheckDataState()

            withContext(Dispatchers.Main) {
                _loginState.value = result
            }
        }

    fun googleSignInUserResult(userResult: UserResult) {
        _loginState.value = userResult.toCheckDataState()
    }
}