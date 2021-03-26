package com.koleychik.core_authorization

import com.koleychik.core_database.FirebaseDatabaseRepository
import com.koleychik.models.User
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(private val firebaseDatabaseRepository: FirebaseDatabaseRepository) {

    @Volatile
    private var user: User? = null

    private fun getUser(): User? {
        if (user == null) synchronized(AuthenticationRepository::class.java) {
            if (user == null) user = getUserFromFirebase()
        }
        return user
    }

    private fun getUserFromFirebase(): User? {
//        val firebaseAuth = FirebaseAuth.getInstance()
//        val firebaseUser = firebaseAuth.currentUser
//        return if (firebaseUser == null) null
//        else firebaseDatabaseRepository.getUserFromDb(firebaseUser.uid, lis)
        TODO("get user from db")
    }

}