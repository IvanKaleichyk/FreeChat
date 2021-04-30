package com.koleychik.freechat.activities

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.koleychik.dialogs.DialogInfo
import com.koleychik.dialogs.DialogInfoListener
import com.koleychik.freechat.App
import com.koleychik.freechat.MainDataSource
import com.koleychik.freechat.Navigator
import com.koleychik.freechat.R
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    internal lateinit var dataSource: MainDataSource

    @Inject
    internal lateinit var navigator: Navigator

    private val navController by lazy {
        findNavController(R.id.navController)
    }

//    private val verificationEmailDialog by lazy {
//        val alertDialog =
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1)
//                AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert)
//            else AlertDialog.Builder(this)
//
//        alertDialog.apply {
//            setTitle(R.string.please_verification_email)
//            setPositiveButton(R.string.ok) { dialogInterface, _ -> dialogInterface.dismiss() }
//        }
//        alertDialog.create()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.component.inject(this)

        checkVerificationEmail()
    }

    private fun checkVerificationEmail() {
        if (dataSource.checkVerification()) return
//        verificationEmailDialog.show()
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
        dataSource.subscribeToUserChanges()
        dataSource.isUserOnline(true)
        navigator.controller = navController
    }

    override fun onStop() {
        super.onStop()
        dataSource.unSubscribeToUserChanges()
        dataSource.isUserOnline(false)
    }
}