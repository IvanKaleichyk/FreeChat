package com.koleychik.freechat

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.kaleichyk.feature_searching.SearchingFeatureNavigationApi
import com.kaleichyk.feature_searching.di.SearchingFeatureApi
import com.kaleichyk.feature_searching.ui.SearchingFragment
import com.koleychik.feature_all_dialogs.AllDialogFeatureNavigationApi
import com.koleychik.feature_all_dialogs.di.AllDialogsFeatureApi
import com.koleychik.feature_all_dialogs.ui.AllDialogsFragment
import com.koleychik.feature_password_utils.SpecifyEmailNavigationApi
import com.koleychik.feature_password_utils.di.PasswordUtilsFeatureApi
import com.koleychik.feature_password_utils.di.PasswordUtilsFeatureComponentHolder
import com.koleychik.feature_password_utils.ui.SpecifyEmailFragment
import com.koleychik.feature_sign.di.SignFeatureApi
import com.koleychik.feature_sign.di.SignFeatureComponentHolder
import com.koleychik.feature_sign.navigation.SignInNavigationApi
import com.koleychik.feature_sign.navigation.SignUpNavigationApi
import com.koleychik.feature_sign.ui.screens.SignInFragment
import com.koleychik.feature_sign.ui.screens.SignUpFragment
import com.koleychik.feature_start.StartFeatureNavigation
import com.koleychik.feature_start.di.StartFeatureApi
import com.koleychik.feature_start.di.StartFeatureComponentHolder
import com.koleychik.feature_start.ui.screens.StartFragment
import com.koleychik.feature_start.ui.screens.VerifyEmailFragment
import com.koleychik.freechat.activities.MainActivity
import com.koleychik.module_injector.NavigationSystem
import javax.inject.Provider

class Navigator(
    private val context: Context,
    private val startFeatureApi: Provider<StartFeatureApi>,
    private val signFeatureApi: Provider<SignFeatureApi>,
    private val passwordUtilsFeatureApi: Provider<PasswordUtilsFeatureApi>,
    private val allDialogsFeatureApi: Provider<AllDialogsFeatureApi>,
    private val searchingFeatureApi: Provider<SearchingFeatureApi>
) : SignInNavigationApi, SignUpNavigationApi, StartFeatureNavigation, SpecifyEmailNavigationApi,
    AllDialogFeatureNavigationApi, SearchingFeatureNavigationApi {

    internal var controller: NavController? = null

    init {
        NavigationSystem.onStartFeature = {
            getApiByFragment(it)
        }
    }

    private fun getApiByFragment(fragment: Fragment) {
        when (fragment) {
            is SpecifyEmailFragment -> passwordUtilsFeatureApi.get()
            is SignUpFragment, is SignInFragment -> signFeatureApi.get()
            is StartFragment, is VerifyEmailFragment -> startFeatureApi.get()
            is AllDialogsFragment -> allDialogsFeatureApi.get()
            is SearchingFragment -> searchingFeatureApi.get()
        }
    }

    override fun fromStartFragmentToAuthorization(bundle: Bundle?) {
        checkController()
        if (controller!!.currentDestination?.id == R.id.startFragment) {
            controller?.navigate(R.id.action_startFragment_to_signUpFragment)
            StartFeatureComponentHolder.reset()
        }
    }

    override fun fromStartFragmentToMainScreen(bundle: Bundle?) {
        checkController()
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    override fun fromStartFragmentToVerifyEmailFragment(bundle: Bundle?) {
        checkController()
        if (controller!!.currentDestination?.id == R.id.startFragment) {
            controller?.navigate(R.id.action_startFragment_to_verifyEmailFragment)
        }
    }

    override fun fromVerifyEmailFragmentToMainScreen(bundle: Bundle?) {
        checkController()
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    override fun fromSignUpToSignIn(bundle: Bundle?) {
        checkController()
        if (controller!!.currentDestination?.id == R.id.signUpFragment) {
            controller?.navigate(R.id.action_signUpFragment_to_signInFragment)
        }
    }

    override fun fromSignUpToMainScreen(bundle: Bundle?) {
        checkController()
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    override fun fromSignInFragmentToMainScreen(bundle: Bundle?) {
        checkController()

    }

    override fun fromSignInFragmentToPasswordRecovery(bundle: Bundle?) {
        checkController()
        if (controller!!.currentDestination?.id == R.id.signInFragment) {
            controller?.navigate(R.id.action_signInFragment_to_specifyEmailFragment)
            SignFeatureComponentHolder.reset()
        }
    }

    override fun fromSignInFragmentToSignUp(bundle: Bundle?) {
        checkController()
        if (controller!!.currentDestination?.id == R.id.signInFragment) {
            controller?.navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }

    override fun fromSpecifyEmailToSignIn(bundle: Bundle?) {
        checkController()
        if (controller!!.currentDestination?.id == R.id.specifyEmailFragment) {
            controller?.navigate(R.id.action_specifyEmailFragment_to_signInFragment)
            PasswordUtilsFeatureComponentHolder.reset()
        }
    }

    private fun checkController() {
        if (controller == null) throw NullPointerException("NavController is not binded")
    }

    override fun fromDialogsFeatureGoToMessagesFeature(bundle: Bundle) {
        checkController()
        if (controller!!.currentDestination?.id == R.id.allDialogsFragment) {
            TODO("GO TO MESSAGES FEATURE")
        }
    }

    override fun fromDialogsFeatureGoToSearchingFeature(bundle: Bundle?) {
        checkController()
        if (controller!!.currentDestination?.id == R.id.allDialogsFragment) {
            controller!!.navigate(R.id.action_allDialogsFragment_to_searchingFragment)
        }
    }

    override fun fromDialogsFeatureGoToUserInfoFeature(bundle: Bundle?) {
        checkController()
        if (controller!!.currentDestination?.id == R.id.allDialogsFragment) {
            TODO("GO TO USER INFO FEATURE")
        }
    }

    override fun fromSearchingFeatureToUserInfoFeature(bundle: Bundle) {
        TODO("Not yet implemented")
    }

}