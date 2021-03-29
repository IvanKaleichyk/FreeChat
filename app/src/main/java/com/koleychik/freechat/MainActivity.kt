package com.koleychik.freechat

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.koleychik.core_authorization.api.AuthenticationRepository
import com.koleychik.core_authorization.result.user.UserResult
import com.koleychik.module_injector.Constants
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    @Inject
    internal lateinit var authenticationRepository: AuthenticationRepository

    private val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        checkPermission()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        TODO("inject in component")
    }

    private fun startActivity() {

    }

    @AfterPermissionGranted(123)
    private fun checkPermission() {
        if (EasyPermissions.hasPermissions(applicationContext, *permissions)) checkUser()
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

    private fun checkUser() {
        authenticationRepository.checkUser()?.let {
            authenticationRepository.loginUser(it) { result ->
                if (result is UserResult.Successful) {
                    startActivity()
                    TODO("GO TO DIALOG_FRAGMENT")
                } else TODO("GO TO LOGIN SCREEN")
            }
        }
    }


}