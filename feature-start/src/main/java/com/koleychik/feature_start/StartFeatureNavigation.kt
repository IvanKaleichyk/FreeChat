package com.koleychik.feature_start

import android.os.Bundle

interface StartFeatureNavigation {

    fun fromStartFragmentGoToAuthorization(bundle: Bundle? = null)
    fun fromStartFragmentGoToMainScreen(bundle: Bundle? = null)
    fun fromStartFragmentGoToInfoFragment(bundle: Bundle? = null)

}