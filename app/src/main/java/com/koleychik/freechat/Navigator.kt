package com.koleychik.freechat

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.koleychik.feature_sign.di.SignFeatureApi
import com.koleychik.feature_sign.navigation.SignUpNavigationApi
import com.koleychik.feature_sign.ui.screens.SignInFragment
import com.koleychik.feature_sign.ui.screens.SignUpFragment
import com.koleychik.feature_start.StartFeatureNavigation
import com.koleychik.feature_start.di.StartFeatureApi
import com.koleychik.feature_start.ui.StartFragment
import com.koleychik.module_injector.NavigationSystem
import javax.inject.Provider

class Navigator(
    private val startFeatureApi: Provider<StartFeatureApi>,
    private val signFeatureApi: Provider<SignFeatureApi>
) : SignUpNavigationApi,
    StartFeatureNavigation {

    internal var controller: NavController? = null

    init {
        NavigationSystem.onStartFeature = {
            getApiByFragment(it)
        }
    }

    private fun getApiByFragment(fragment: Fragment) {
        when (fragment) {
            is SignUpFragment, is SignInFragment -> signFeatureApi.get()
            is StartFragment -> startFeatureApi.get()
        }
    }

    override fun fromStartFragmentGoToAuthorization(bundle: Bundle?) {
        checkController()
        if (controller!!. currentDestination?.id == R.id.startFragment){
            controller?.navigate(R.id.action_startFragment_to_signUpFragment)
        }
    }

    override fun fromStartFragmentGoToMainScreen(bundle: Bundle?) {
        checkController()
        if (controller!!. currentDestination?.id == R.id.startFragment){
            controller?.navigate(R.id.action_startFragment_to_allDialogsFragment)
        }
    }

    override fun fromSignUpToSignIn(bundle: Bundle?) {
        checkController()
        if (controller!!. currentDestination?.id == R.id.signInFragment){
            controller?.navigate(R.id.action_signUpFragment_to_signInFragment)
        }
    }

    override fun fromSignUpToMainScreen(bundle: Bundle?) {
        checkController()
        if (controller!!. currentDestination?.id == R.id.signInFragment){
            controller?.navigate(R.id.action_signUpFragment_to_allDialogsFragment)
        }
    }

    private fun checkController(){
        if (controller == null) throw NullPointerException("NavController is not binded")
    }

}