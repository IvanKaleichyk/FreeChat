package com.koleychik.core_authorization

import com.google.firebase.auth.FirebaseAuth
import com.koleychik.core_database.FirebaseDatabaseRepository
import com.koleychik.models.User
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val firebaseAuthorization: FirebaseAuthorization,
    private val firebaseDatabaseRepository: FirebaseDatabaseRepository
) {

    @Volatile
    private var user: User? = null

    private fun getUser(): User? {
//        if (user == null) synchronized(AuthenticationRepository::class.java) {
//            if (user == null)
//        }
        return user
    }

    private fun getUserFromFirebase() {
        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null) null
//        else firebaseDatabaseRepository.getUserFromDb(firebaseUser.uid){ userResult ->
//
//        }
    }

}