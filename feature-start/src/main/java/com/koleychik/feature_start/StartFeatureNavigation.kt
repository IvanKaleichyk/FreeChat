package com.koleychik.feature_start

import android.os.Bundle

interface StartFeatureNavigation {

    fun fromStartFragmentGoToAuthorization(bundle: Bundle?)
    fun fromStartFragmentGoToMainScreen(bundle: Bundle?)

}