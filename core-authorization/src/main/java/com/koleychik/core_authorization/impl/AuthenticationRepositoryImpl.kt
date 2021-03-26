package com.koleychik.core_authorization.impl

import com.google.firebase.auth.FirebaseAuth
import com.koleychik.core_authorization.FirebaseAuthorization
import com.koleychik.core_authorization.api.AuthenticationRepository
import com.koleychik.core_authorization.api.FirebaseDatabaseRepository
import com.koleychik.core_authorization.result.CheckResult
import com.koleychik.core_authorization.result.user.UserResult
import com.koleychik.models.User
import javax.inject.Inject

internal class AuthenticationRepositoryImpl @Inject constructor(
    private val firebaseAuthorization: FirebaseAuthorization,
    private val firebaseDatabaseRepository: FirebaseDatabaseRepository
) : AuthenticationRepository {

    // must be init that application is started
    lateinit var user: User

    override fun loginUsingGoogle() {
        TODO(" SIGN IN USING GOOGLE")
    }

    override fun getUserFromFirebase(res: (UserResult) -> Unit) {
        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null) res(UserResult.UserNotInitialized)
        else firebaseDatabaseRepository.getUserFromDb(firebaseUser.uid, res)
    }

    override fun login(email: String, password: String, res: (CheckResult) -> Unit) =
        firebaseAuthorization.login(email, password, res)

    override fun createAccount(email: String, password: String, res: (CheckResult) -> Unit) =
        firebaseAuthorization.createAccount(email, password, res)

}