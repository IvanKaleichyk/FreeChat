package com.koleychik.core_authentication.impl

import android.net.Uri
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ListenerRegistration
import com.kaleichyk.data.CurrentUser
import com.kaleichyk.data.showLog
import com.koleychik.core_authentication.api.AuthDbDataSource
import com.koleychik.core_authentication.getObject
import com.koleychik.core_authentication.toCheckResultError
import com.koleychik.core_authentication.toUserResultError
import com.koleychik.models.constants.UserConstants
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.user.UserResult
import com.koleychik.models.users.User
import com.koleychik.models.users.UserRoot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class AuthDbDataSourceImpl @Inject constructor() : AuthDbDataSource {

    private val db = FirebaseFirestore.getInstance()

    private lateinit var userListener: ListenerRegistration

    override suspend fun addUser(user: User): UserResult {
        val document = db.collection(UserConstants.ROOT_PATH).document(user.id)

        return try {
            document.set(user).await()
            UserResult.Successful(user)
        } catch (e: FirebaseAuthException) {
            e.toUserResultError()
        }
    }

    override suspend fun getUserByUid(uid: String): UserResult {
        val document = db.collection(UserConstants.ROOT_PATH).document(uid)

        return try {
            val result = document.get().await()
            UserResult.Successful(result.getObject(User::class.java))
        } catch (e: FirebaseFirestoreException) {
            e.toUserResultError()
        }
    }

    override suspend fun updateUserEmail(email: String): CheckResult {
        val document = db.collection(UserConstants.ROOT_PATH).document(CurrentUser.user!!.id)

        return try {
            document.update(UserConstants.EMAIL, email).await()
            CheckResult.Successful
        } catch (e: FirebaseFirestoreException) {
            e.toCheckResultError()
        }
    }

    override suspend fun updateName(name: String): CheckResult {
        val document = db.collection(UserConstants.ROOT_PATH).document(CurrentUser.user!!.id)
        return try {
            document.update(UserConstants.NAME, name).await()
            CheckResult.Successful
        } catch (e: FirebaseFirestoreException) {
            e.toCheckResultError()
        }
    }

    override suspend fun updateIcon(uri: Uri): CheckResult {
        val document = db.collection(UserConstants.ROOT_PATH).document(CurrentUser.user!!.id)
        return try {
            document.update(UserConstants.ICON, uri.toString()).await()
            CheckResult.Successful
        } catch (e: FirebaseFirestoreException) {
            e.toCheckResultError()
        }
    }

    override suspend fun updateBackground(uri: Uri): CheckResult {
        val document = db.collection(UserConstants.ROOT_PATH).document(CurrentUser.user!!.id)
        return try {
            document.update(UserConstants.BACKGROUND, uri.toString()).await()
            CheckResult.Successful
        } catch (e: FirebaseFirestoreException) {
            e.toCheckResultError()
        }
    }

    override fun isUserOnline(isOnline: Boolean) {
        db.collection(UserConstants.ROOT_PATH)
            .document(CurrentUser.user!!.id)
            .update(UserConstants.IS_ONLINE, isOnline)
    }

    override fun subscribeToUserChanges() {
        val query = db.collection(UserConstants.ROOT_PATH).document(CurrentUser.user!!.id)
        userListener = query.addSnapshotListener { value, error ->
            if (error != null) {
                showLog("subscribeToUserChanges ${error.message}")
                CurrentUser.user = value?.toObject(UserRoot::class.java)
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