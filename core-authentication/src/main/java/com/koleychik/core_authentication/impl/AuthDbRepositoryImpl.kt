package com.koleychik.core_authentication.impl

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.koleychik.core_authentication.R
import com.koleychik.core_authentication.api.AuthDbRepository
import com.koleychik.core_authentication.constants.UserConstants
import com.koleychik.core_authentication.result.user.UserResult
import com.koleychik.models.User
import com.koleychik.module_injector.Constants
import javax.inject.Inject

internal class AuthDbRepositoryImpl @Inject constructor() : AuthDbRepository {

    private val db = FirebaseDatabase.getInstance()

    override fun addUser(user: User, res: (UserResult) -> Unit) {
        Log.d(Constants.TAG, "AuthDbRepositoryImpl start addUser")
        val ref = db.getReference("${UserConstants.ROOT_PATH}/${user.id}")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val model = snapshot.getValue(User::class.java)
                if (model == null) res(UserResult.DataError(R.string.cannot_create_user))
                else res(UserResult.Successful(model))
            }

            override fun onCancelled(error: DatabaseError) {
                res(UserResult.ServerError(error.message))
            }
        })

        ref.setValue(user)
    }

    override fun getUserByUid(uid: String, res: (UserResult) -> Unit) {
        Log.d(Constants.TAG, "AuthDbRepositoryImpl start getUserByUid")
        db.getReference("${UserConstants.ROOT_PATH}/$uid")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    if (user == null) res(UserResult.UserNotExists)
                    else res(UserResult.Successful(user))
                }

                override fun onCancelled(error: DatabaseError) {
                    res(UserResult.ServerError(error.message))
                }

            })
    }
}