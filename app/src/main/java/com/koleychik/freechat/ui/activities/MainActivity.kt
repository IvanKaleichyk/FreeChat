package com.koleychik.freechat.ui.activities

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.koleychik.dialogs.DialogInfo
import com.koleychik.freechat.App
import com.koleychik.freechat.Navigator
import com.koleychik.freechat.R
import com.koleychik.freechat.managers.MainManager
import com.koleychik.freechat.ui.DialogEmailVerification
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    internal lateinit var manager: MainManager

    @Inject
    internal lateinit var navigator: Navigator

    private val navController by lazy {
        findNavController(R.id.navController)
    }

    private var dialogVerificationEmail: DialogInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.component.inject(this)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        checkVerificationEmail()
    }

    private fun checkVerificationEmail() {
        if (manager.checkVerification()) return
        dialogVerificationEmail = DialogEmailVerification().apply {
            show(supportFragmentManager, "VerificationEmailDialogTAG")
        }
    }


    override fun onStart() {
        super.onStart()
        manager.subscribeToUserChanges()
        manager.isUserOnline(true)
        navigator.controller = navController
    }

    override fun onStop() {
        super.onStop()
        manager.unSubscribeToUserChanges()
        manager.isUserOnline(false)
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id != R.id.allDialogsFragment) super.onBackPressed()
    }
}