package com.koleychik.core_authentication.result

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthCredential

sealed class GoogleSignInResult {

    class Successful(val credential: AuthCredential, val account: GoogleSignInAccount) :
        GoogleSignInResult()

    class ServerError(val message: String) : GoogleSignInResult()
    class DataError(val message: Int) : GoogleSignInResult()

}