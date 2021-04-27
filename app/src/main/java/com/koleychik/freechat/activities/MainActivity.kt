package com.koleychik.freechat.activities

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
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

    private val verificationEmailDialog by lazy {
        val alertDialog =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1)
                AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert)
            else AlertDialog.Builder(this)

        alertDialog.apply {
            setTitle(R.string.please_verification_email)
            setPositiveButton(R.string.ok) { dialogInterface, _ -> dialogInterface.cancel() }
        }
        alertDialog.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.component.inject(this)

        checkVerificationEmail()
    }

    private fun checkVerificationEmail() {
        if (dataSource.checkVerification()) return
        verificationEmailDialog.show()
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