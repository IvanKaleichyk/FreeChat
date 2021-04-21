package com.koleychik.freechat.activities

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.koleychik.freechat.App
import com.koleychik.freechat.Navigator
import com.koleychik.freechat.R
import com.koleychik.module_injector.Constants
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

class AuthenticationActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    @Inject
    internal lateinit var navigator: Navigator

    private val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

    private val controller by lazy { findNavController(R.id.navController) }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_FreeChat)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        App.component.inject(this)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        checkPermission()
    }

    override fun onStart() {
        super.onStart()
        navigator.controller = controller
    }

    @AfterPermissionGranted(123)
    private fun checkPermission() {
        if (EasyPermissions.hasPermissions(applicationContext, *permissions))
        else {
            EasyPermissions.requestPermissions(
                this,
                applicationContext.getString(R.string.cannot_without_permissions),
                123,
                *permissions
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d(Constants.TAG, "onRequestPermissionsResult")
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.d(Constants.TAG, "onPermissionsGranted")
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(applicationContext, R.string.cannot_without_permissions, Toast.LENGTH_LONG)
            .show()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        navigator.controller = null
    }

    override fun onBackPressed() {
        when (controller.currentDestination?.id) {
            R.id.signInFragment, R.id.signUpFragment -> {
            }
            else -> super.onBackPressed()

        }
    }

}