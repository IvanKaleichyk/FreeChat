package com.koleychik.feature_sing.navigation

import android.os.Bundle

interface SignUpNavigationApi {

    fun fromSignUpToSignIn(bundle: Bundle? = null)
    fun fromSignUpToMainScreen(bundle: Bundle? = null)

}