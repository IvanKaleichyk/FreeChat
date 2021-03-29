package com.koleychik.core_authorization.impl

import android.app.Activity
import androidx.activity.result.ActivityResultRegistry
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
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

    private val auth = FirebaseAuth.getInstance()

    private var user: User? = null

    override fun getUser(): User? = user

    override fun Activity.loginUsingGoogle(activityResultRegistry: ActivityResultRegistry) {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val signInClient = GoogleSignIn.getClient(this, options)

        TODO(" SIGN IN USING GOOGLE")
    }

    override fun checkUser(): String? = auth.currentUser?.uid

    override fun loginUser(uid: String, res: (UserResult) -> Unit) {
        firebaseDatabaseRepository.getUserFromDb(uid) {
            res(it)
            if (it is UserResult.Successful) user = it.user
        }
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