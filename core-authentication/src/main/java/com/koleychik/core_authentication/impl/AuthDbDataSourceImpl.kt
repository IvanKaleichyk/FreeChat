package com.koleychik.core_authentication.impl

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.kaleichyk.data.CurrentUser
import com.kaleichyk.data.showLog
import com.koleychik.core_authentication.api.AuthDbDataSource
import com.koleychik.models.constants.UserConstants
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.user.UserResult
import com.koleychik.models.users.User
import com.koleychik.models.users.UserRoot
import com.koleychik.module_injector.Constants
import javax.inject.Inject

internal class AuthDbDataSourceImpl @Inject constructor() : AuthDbDataSource {

    private val db = FirebaseFirestore.getInstance()

    private lateinit var userListener: ListenerRegistration

    override fun addUser(user: User, res: (UserResult) -> Unit) {
        Log.d(Constants.TAG, "AuthDbDataSourceImpl start addUser")
        db.collection(UserConstants.ROOT_PATH)
            .document(user.id)
            .set(user)
            .addOnSuccessListener { res(UserResult.Successful(user)) }
            .addOnFailureListener {
                if (it.localizedMessage != null) res(UserResult.ServerError(it.localizedMessage!!))
                else res(UserResult.ServerError(it.message.toString()))
            }
    }

    override fun getUserByUid(uid: String, res: (UserResult) -> Unit) {
        Log.d(Constants.TAG, "AuthDbDataSourceImpl start getUserByUid")
        db.collection(UserConstants.ROOT_PATH)
            .document(uid)
            .get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)
                if (user == null) res(UserResult.UserNotExists)
                else res(UserResult.Successful(user))
            }
            .addOnFailureListener {
                if (it.localizedMessage != null) res(UserResult.ServerError(it.localizedMessage!!))
                else res(UserResult.ServerError(it.message.toString()))
            }
    }

    override fun updateUserEmail(email: String, res: (CheckResult) -> Unit) {
        db.collection(UserConstants.ROOT_PATH)
            .document(CurrentUser.user!!.id)
            .collection(CurrentUser.user!!.id)
            .document(UserConstants.EMAIL)
            .set(email)
            .addOnSuccessListener {
                CurrentUser.user?.email = email
                res(CheckResult.Successful)
            }
            .addOnFailureListener {
                if (it.localizedMessage != null) res(CheckResult.ServerError(it.localizedMessage!!))
                else res(CheckResult.ServerError(it.message.toString()))
            }
    }

    override fun updateName(name: String, res: (CheckResult) -> Unit) {
        db.collection(UserConstants.ROOT_PATH)
            .document(CurrentUser.user!!.id)
            .collection(CurrentUser.user!!.id)
            .document(UserConstants.NAME)
            .set(name)
            .addOnSuccessListener {
                CurrentUser.user?.name = name
                res(CheckResult.Successful)
            }
            .addOnFailureListener {
                if (it.localizedMessage != null) res(CheckResult.ServerError(it.localizedMessage!!))
                else res(CheckResult.ServerError(it.message.toString()))
            }
    }

    override fun updateIcon(uri: Uri, res: (CheckResult) -> Unit) {
        db.collection(UserConstants.ROOT_PATH)
            .document(CurrentUser.user!!.id)
            .collection(CurrentUser.user!!.id)
            .document(UserConstants.ICON)
            .set(uri)
            .addOnSuccessListener {
                CurrentUser.user?.icon = uri
                res(CheckResult.Successful)
            }
            .addOnFailureListener {
                if (it.localizedMessage != null) res(CheckResult.ServerError(it.localizedMessage!!))
                else res(CheckResult.ServerError(it.message.toString()))
            }
    }

    override fun updateBackground(uri: Uri, res: (CheckResult) -> Unit) {
        db.collection(UserConstants.ROOT_PATH)
            .document(CurrentUser.user!!.id)
            .collection(CurrentUser.user!!.id)
            .document(UserConstants.BACKGROUND)
            .set(uri)
            .addOnSuccessListener {
                CurrentUser.user?.background = uri
                res(CheckResult.Successful)
            }
            .addOnFailureListener {
                if (it.localizedMessage != null) res(CheckResult.ServerError(it.localizedMessage!!))
                else res(CheckResult.ServerError(it.message.toString()))
            }
    }

    override fun isUserOnline(isOnline: Boolean) {
        val map = mapOf(UserConstants.IS_ONLINE to isOnline)
        db.collection(UserConstants.ROOT_PATH)
            .document(CurrentUser.user!!.id)
            .update(map)
    }

    override fun subscribeToUserChanges() {
        val query = db.collection(UserConstants.ROOT_PATH).document(CurrentUser.user!!.id)
        userListener = query.addSnapshotListener { value, error ->
            if (error != null) {
                showLog("subscribeToUserChanges ${error.message}")
                return@addSnapshotListener
            }
            showLog("subscribeToUserChanges successful")

            if (value != null) CurrentUser.user = value.toObject(UserRoot::class.java)
            else showLog("subscribeToUserChanges value == null")
        }
    }

    override fun unSubscribeToUserChanges() {
        userListener.remove()
    }

}