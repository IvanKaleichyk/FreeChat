package com.koleychik.core_authentication.impl

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.koleychik.core_authentication.R
import com.koleychik.core_authentication.api.AccountRepository
import com.koleychik.core_authentication.constants.UserConstants
import com.koleychik.core_authentication.result.CheckResult
import com.koleychik.core_authentication.result.user.UserResult
import com.koleychik.models.User
import com.koleychik.module_injector.Constants.TAG
import javax.inject.Inject

internal class AccountRepositoryImpl @Inject constructor() : AccountRepository {

    private val db = FirebaseDatabase.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override var user: User? = null

    override fun isEmailVerified(): Boolean = auth.currentUser?.isEmailVerified ?: false

    override fun verifyEmail(res: (CheckResult) -> Unit) {
        Log.d(TAG, "AccountRepositoryImpl start verifyEmail")
        val fbUser = auth.currentUser
        if (fbUser == null) {
            res(CheckResult.DataError(R.string.cannot_find_user))
        } else {
            fbUser.sendEmailVerification().addOnCompleteListener {
                if (it.isSuccessful) res(CheckResult.Successful)
                else res(CheckResult.ServerError(it.exception?.message.toString()))
            }
        }
    }

    override fun updateEmail(email: String, res: (UserResult) -> Unit) {
        Log.d(TAG, "AccountRepositoryImpl start updateEmail")
        if (user == null) res(UserResult.UserNotInitialized)
        else {
            val ref =
                db.getReference("${UserConstants.ROOT_PATH}/${user!!.id}/${UserConstants.EMAIL}")
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    auth.currentUser!!.updateEmail(email)
                    user?.email = email
                    res(UserResult.Successful(user!!))
                }

                override fun onCancelled(error: DatabaseError) {
                    res(UserResult.ServerError(error.message))
                }
            })
            ref.setValue(email)
        }
    }

    override fun updatePassword(password: String, res: (UserResult) -> Unit) {
        Log.d(TAG, "AccountRepositoryImpl start updatePassword")
        auth.currentUser!!.updatePassword(password)
    }

    override fun updateName(name: String, res: (UserResult) -> Unit) {
        Log.d(TAG, "AccountRepositoryImpl start updateName")
        if (user == null) res(UserResult.UserNotInitialized)
        else {
            val ref =
                db.getReference("${UserConstants.ROOT_PATH}/${user!!.id}/${UserConstants.NAME}")
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    user?.name = name
                    res(UserResult.Successful(user!!))
                }

                override fun onCancelled(error: DatabaseError) {
                    res(UserResult.ServerError(error.message))
                }
            })
            ref.setValue(name)
        }
    }

    override fun updateIcon(uri: Uri, res: (UserResult) -> Unit) {
        Log.d(TAG, "AccountRepositoryImpl start updateIcon")
        if (user == null) res(UserResult.UserNotInitialized)
        else {
            val ref =
                db.getReference("${UserConstants.ROOT_PATH}/${user!!.id}/${UserConstants.ICON}")
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    user?.icon = uri
                    res(UserResult.Successful(user!!))
                }

                override fun onCancelled(error: DatabaseError) {
                    res(UserResult.ServerError(error.message))
                }
            })
            ref.setValue(uri)
        }
    }

    override fun updateBackground(uri: Uri, res: (UserResult) -> Unit) {
        Log.d(TAG, "AccountRepositoryImpl start updateBackground")
        if (user == null) res(UserResult.UserNotInitialized)
        else {
            val ref =
                db.getReference("${UserConstants.ROOT_PATH}/${user!!.id}/${UserConstants.BACKGROUND}")
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    user?.background = uri
                    res(UserResult.Successful(user!!))
                }

                override fun onCancelled(error: DatabaseError) {
                    res(UserResult.ServerError(error.message))
                }
            })
            ref.setValue(uri)
        }
    }

}