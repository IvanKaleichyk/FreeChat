package com.koleychik.core_authorization.newapiimpl

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.koleychik.core_authorization.constants.UserConstants
import com.koleychik.core_authorization.newapi.AccountRepository
import com.koleychik.core_authorization.result.user.UserResult
import com.koleychik.models.User

internal class AccountRepositoryImpl : AccountRepository {

    private val db = FirebaseDatabase.getInstance()
    private val fbUser = FirebaseAuth.getInstance().currentUser!!

    override var user: User? = null

    override fun updateEmail(email: String, res: (UserResult) -> Unit) {
        if (user == null) res(UserResult.UserNotInitialized)
        else {
            val ref =
                db.getReference("${UserConstants.ROOT_PATH}/${user!!.id}/${UserConstants.EMAIL}")
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    fbUser.updateEmail(email)
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
        fbUser.updatePassword(password)
    }

    override fun updateName(name: String, res: (UserResult) -> Unit) {
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