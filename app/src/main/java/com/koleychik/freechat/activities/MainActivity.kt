package com.koleychik.freechat.activities

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.koleychik.dialogs.DialogInfo
import com.koleychik.dialogs.DialogInfoListener
import com.koleychik.freechat.App
import com.koleychik.freechat.Navigator
import com.koleychik.freechat.R
import com.koleychik.freechat.managers.MainManager
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    internal lateinit var manager: MainManager

    @Inject
    internal lateinit var navigator: Navigator

    private val navController by lazy {
        findNavController(R.id.navController)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.component.inject(this)

        checkVerificationEmail()
    }

    private fun checkVerificationEmail() {
        if (manager.checkVerification()) return
        val dialogListener = object : DialogInfoListener {
            override fun onClick(dialog: DialogInterface) {
                dialog.dismiss()
            }
        }
        DialogInfo(dialogListener, getString(R.string.please_verification_email), null).show(
            supportFragmentManager,
            "VerificationEmailDialogTAG"
        )
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
}