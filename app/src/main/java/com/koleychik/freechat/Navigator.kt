package com.koleychik.freechat

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.kaleichyk.feature_messages.di.MessagesFeatureApi
import com.kaleichyk.feature_messages.ui.MessagesFragment
import com.kaleichyk.feature_searching.SearchingFeatureNavigationApi
import com.kaleichyk.feature_searching.di.SearchingFeatureApi
import com.kaleichyk.feature_searching.ui.SearchingFragment
import com.kaleichyk.feature_user_info.UserInfoNavigationApi
import com.kaleichyk.feature_user_info.di.UserInfoFeatureApi
import com.kaleichyk.feature_user_info.ui.UserInfoFragment
import com.kaleichyk.utils.navigation.NavigationSystem
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
import com.koleychik.freechat.ui.activities.AuthenticationActivity
import com.koleychik.freechat.ui.activities.MainActivity
import javax.inject.Provider

class Navigator(
    private val context: Context,
    private val startFeatureApi: Provider<StartFeatureApi>,
    private val signFeatureApi: Provider<SignFeatureApi>,
    private val passwordUtilsFeatureApi: Provider<PasswordUtilsFeatureApi>,
    private val allDialogsFeatureApi: Provider<AllDialogsFeatureApi>,
    private val searchingFeatureApi: Provider<SearchingFeatureApi>,
    private val userInfoFeatureApi: Provider<UserInfoFeatureApi>,
    private val messagesFeatureApi: Provider<MessagesFeatureApi>,
) : SignInNavigationApi, SignUpNavigationApi, StartFeatureNavigation, SpecifyEmailNavigationApi,
    AllDialogFeatureNavigationApi, SearchingFeatureNavigationApi, UserInfoNavigationApi {

    internal var controller: NavController? = null

    init {
        NavigationSystem.onStartFeature = {
            getApiByFragment(it)
        }
    }

    override fun fromStartFragmentToAuthorization(bundle: Bundle?) {
        checkController()
        if (controller!!.currentDestination?.id == R.id.startFragment) {
            controller?.navigate(R.id.action_startFragment_to_signUpFragment, bundle)
            StartFeatureComponentHolder.reset()
        }
    }

    override fun fromStartFragmentToMainScreen() {
        checkController()
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    override fun fromStartFragmentToVerifyEmailFragment(bundle: Bundle?) {
        checkController()
        if (controller!!.currentDestination?.id == R.id.startFragment) {
            controller?.navigate(R.id.action_startFragment_to_verifyEmailFragment, bundle)
        }
    }

    override fun fromVerifyEmailFragmentToMainScreen() {
        checkController()
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    override fun fromSignUpToSignIn(bundle: Bundle?) {
        checkController()
        if (controller!!.currentDestination?.id == R.id.signUpFragment) {
            controller?.navigate(R.id.action_signUpFragment_to_signInFragment, bundle)
        }
    }

    override fun fromSignUpToMainScreen() {
        checkController()
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    override fun fromSignInFragmentToMainScreen() {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    override fun fromSignInFragmentToPasswordRecovery(bundle: Bundle?) {
        checkController()
        if (controller!!.currentDestination?.id == R.id.signInFragment) {
            controller?.navigate(R.id.action_signInFragment_to_specifyEmailFragment, bundle)
            SignFeatureComponentHolder.reset()
        }
    }

    override fun fromSignInFragmentToSignUp(bundle: Bundle?) {
        checkController()
        if (controller!!.currentDestination?.id == R.id.signInFragment) {
            controller?.navigate(R.id.action_signInFragment_to_signUpFragment, bundle)
        }
    }

    override fun fromSpecifyEmailToSignIn(bundle: Bundle?) {
        checkController()
        if (controller!!.currentDestination?.id == R.id.specifyEmailFragment) {
            controller?.navigate(R.id.action_specifyEmailFragment_to_signInFragment, bundle)
            PasswordUtilsFeatureComponentHolder.reset()
        }
    }

    override fun fromDialogsFeatureGoToMessagesFeature(bundle: Bundle) {
        checkController()
        if (controller!!.currentDestination?.id == R.id.allDialogsFragment) {
            controller?.navigate(R.id.action_allDialogsFragment_to_messagesFragment, bundle)
        }
    }

    override fun fromDialogsFeatureGoToSearchingFeature(bundle: Bundle?) {
        checkController()
        if (controller!!.currentDestination?.id == R.id.allDialogsFragment) {
            controller!!.navigate(R.id.action_allDialogsFragment_to_searchingFragment, bundle)
        }
    }

    override fun fromDialogsFeatureGoToUserInfoFeature(bundle: Bundle?) {
        checkController()
        if (controller!!.currentDestination?.id == R.id.allDialogsFragment) {
            controller!!.navigate(R.id.action_allDialogsFragment_to_userInfoFragment, bundle)
        }
    }

    override fun fromSearchingFeatureToUserInfoFeature(bundle: Bundle) {
        checkController()
        if (controller!!.currentDestination?.id == R.id.searchingFragment) {
            controller!!.navigate(R.id.action_searchingFragment_to_userInfoFragment, bundle)
        }
    }

    override fun fromUserInfoFeatureToSignFeature() {
        val intent = Intent(context, AuthenticationActivity::class.java)
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    override fun fromUserInfoFeatureToMessagesFeature(bundle: Bundle) {
        checkController()
        if (controller!!.currentDestination?.id == R.id.userInfoFragment) {
            controller!!.navigate(R.id.action_userInfoFragment_to_messagesFragment, bundle)
        }
    }

    private fun getApiByFragment(fragment: Fragment) {
        when (fragment) {
            is SpecifyEmailFragment -> passwordUtilsFeatureApi.get()
            is SignUpFragment, is SignInFragment -> signFeatureApi.get()
            is StartFragment, is VerifyEmailFragment -> startFeatureApi.get()
            is AllDialogsFragment -> allDialogsFeatureApi.get()
            is SearchingFragment -> searchingFeatureApi.get()
            is UserInfoFragment -> userInfoFeatureApi.get()
            is MessagesFragment -> messagesFeatureApi.get()
        }
    }

    private fun checkController() {
        if (controller == null) throw NullPointerException("NavController is not binded")
    }

}