package com.koleychik.core_authentication.impl

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.koleychik.core_authentication.api.AuthDbRepository
import com.koleychik.core_authentication.constants.UserConstants
import com.koleychik.core_authentication.result.user.UserResult
import com.koleychik.models.users.User
import com.koleychik.module_injector.Constants
import javax.inject.Inject

internal class AuthDbRepositoryImpl @Inject constructor() : AuthDbRepository {

    private val db = FirebaseFirestore.getInstance()

    override fun addUser(user: User, res: (UserResult) -> Unit) {
        Log.d(Constants.TAG, "AuthDbRepositoryImpl start addUser")
        db.collection("${UserConstants.ROOT_PATH}/${user.id}")
            .add(user)
            .addOnSuccessListener { res(UserResult.Successful(user)) }
            .addOnFailureListener { res(UserResult.ServerError(it.message.toString())) }
    }

    override fun getUserByUid(uid: String, res: (UserResult) -> Unit) {
        Log.d(Constants.TAG, "AuthDbRepositoryImpl start getUserByUid")
        db.collection("${UserConstants.ROOT_PATH}/$uid")
            .get()
            .addOnSuccessListener {
                res(UserResult.Successful(it.toObjects(User::class.java)[0]))
            }
            .addOnFailureListener {
                res(UserResult.ServerError(it.message.toString()))
            }
    }
}