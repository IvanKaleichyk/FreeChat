package com.koleychik.core_database

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.koleychik.core_database.PathConstants.USERS_PATH
import com.koleychik.core_database.result.CheckResult
import com.koleychik.core_database.result.ListDbResult
import com.koleychik.core_database.result.SingleDbDataResult
import com.koleychik.core_database.result.user.UserResult
import com.koleychik.core_database.result.user.UsersResult
import com.koleychik.models.User
import com.koleychik.module_injector.Constants.TAG

@Suppress("UNCHECKED_CAST")
class FirebaseDatabaseRepository {

    private val db = FirebaseDatabase.getInstance()

    fun putValue(path: String, value: Any, res: (CheckResult) -> Unit) {
        val ref = db.getReference(path)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                res(CheckResult.Successful)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                res(CheckResult.ServerError(databaseError.message))

            }
        })

        ref.setValue(value)
    }

    fun <T> listValues(path: String, res: (ListDbResult) -> Unit) {
        db.getReference(path).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<T>()
                snapshot.children.forEach {
                    var model: T? = null
                    try {
                        model = it as T?
                    } catch (e: Exception) {
                    }

                    if (model != null) list.add(model)
                }
                if (list.isNotEmpty()) res(ListDbResult.Successful<T>(list))
                else res(ListDbResult.DataError(R.string.cannot_find_information))
            }

            override fun onCancelled(error: DatabaseError) {
                res(ListDbResult.ServerError(error.message))
            }

        })
    }

    fun <T> getValue(path: String, res: (SingleDbDataResult) -> Unit) {
        db.getReference(path).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val model = snapshot.value
                if (model == null) res(SingleDbDataResult.DataError(R.string.cannot_find_information))
                else {
                    var resultValue: T? = null
                    try {
                        resultValue = model as T
                    } catch (e: Exception) {
                        res(SingleDbDataResult.ServerError(e.message.toString()))
                    }
                    if (resultValue != null) res(SingleDbDataResult.Successful<T>(resultValue))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                res(SingleDbDataResult.ServerError(error.message))
            }
        })
    }

    fun getUsers(res: (UsersResult) -> Unit) {
        db.getReference(USERS_PATH).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<User>()
                snapshot.children.forEach {
                    val user = it.getValue(User::class.java)
                    if (user != null) list.add(user)
                }

                if (list.isNotEmpty()) res(UsersResult.Successful(list))
                else res(UsersResult.DataError(R.string.cannot_find_user))

            }

            override fun onCancelled(error: DatabaseError) {
                res(UsersResult.ServerError(error.message))
            }
        })
    }

    fun putUser(user: User, res: (CheckResult) -> Unit) {
        val ref = db.getReference(USERS_PATH)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                res(CheckResult.Successful)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                res(CheckResult.ServerError(databaseError.message))

            }
        })

        ref.setValue(user)
    }

    fun getUserFromDb(uid: String, res: (UserResult) -> Unit) {
        Log.d(TAG, "getUserFromDb uid = $uid")
        db.getReference("$USERS_PATH/$uid")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val model = snapshot.getValue(User::class.java)
                    if (model == null) res(UserResult.DataError(R.string.cannot_find_user))
                    else res(UserResult.Successful(model))
                }

                override fun onCancelled(error: DatabaseError) {
                    res(UserResult.ServiceError(error.message))
                }
            })
    }

}