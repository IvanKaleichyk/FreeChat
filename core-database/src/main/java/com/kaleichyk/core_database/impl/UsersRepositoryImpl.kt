package com.kaleichyk.core_database.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.kaleichyk.core_database.api.UsersRepository
import com.koleychik.models.constants.UserConstants
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.user.UserResult
import com.koleychik.models.results.user.UsersResult
import com.koleychik.models.users.User
import javax.inject.Inject

internal class UsersRepositoryImpl @Inject constructor() : UsersRepository {

    private val store = FirebaseFirestore.getInstance()

    override fun getUsers(
        orderBy: String,
        startAfter: Int,
        limit: Long,
        res: (UsersResult) -> Unit
    ) {
        store.collection(UserConstants.ROOT_PATH)
            .orderBy(orderBy)
            .startAfter(startAfter)
            .limit(limit)
            .get()
            .addOnSuccessListener { result ->
                val listUsers = mutableListOf<User>()
                for (i in result) listUsers.add(i.toObject(User::class.java))
                res(UsersResult.Successful(listUsers))
            }
            .addOnFailureListener {
                if (it.localizedMessage != null) res(UsersResult.ServerError(it.localizedMessage!!))
                else res(UsersResult.ServerError(it.message.toString()))
            }
    }

    override fun getUserById(id: String, res: (UserResult) -> Unit) {
        store.collection(UserConstants.ROOT_PATH)
            .get()
            .addOnSuccessListener {
                res(UserResult.Successful(it.toObjects(User::class.java)[0]))
            }
            .addOnFailureListener {
                if (it.localizedMessage != null) res(UserResult.ServerError(it.localizedMessage!!))
                else res(UserResult.ServerError(it.message.toString()))
            }
    }

    override fun searchByName(name: String, res: (UsersResult) -> Unit) {
        store.collection(UserConstants.ROOT_PATH)
            .orderBy(UserConstants.NAME)
            .startAt(name).endAt(name + '\uf8ff')
            .get()
            .addOnSuccessListener { result ->
                val listUsers = mutableListOf<User>()
                for (i in result) listUsers.add(i.toObject(User::class.java))
                res(UsersResult.Successful(listUsers))
            }
            .addOnFailureListener {
                if (it.localizedMessage != null) res(UsersResult.ServerError(it.localizedMessage!!))
                else res(UsersResult.ServerError(it.message.toString()))
            }
    }

    override fun deleteUser(id: String, res: (CheckResult) -> Unit) {
        store.collection(UserConstants.ROOT_PATH)
            .document(id).delete()
            .addOnSuccessListener {
                res(CheckResult.Successful)
            }
            .addOnFailureListener {
                if (it.localizedMessage != null) res(CheckResult.ServerError(it.localizedMessage!!))
                else res(CheckResult.ServerError(it.message.toString()))
            }
    }
}