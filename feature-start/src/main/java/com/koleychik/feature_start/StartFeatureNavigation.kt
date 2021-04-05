package com.koleychik.feature_start

import android.os.Bundle

interface StartFeatureNavigation {

    fun fromStartFragmentToAuthorization(bundle: Bundle? = null)
    fun fromStartFragmentToMainScreen(bundle: Bundle? = null)
    fun fromStartFragmentToVerifyEmailFragment(bundle: Bundle? = null)
    fun fromVerifyEmailFragmentToMainScreen(bundle: Bundle? = null)

}