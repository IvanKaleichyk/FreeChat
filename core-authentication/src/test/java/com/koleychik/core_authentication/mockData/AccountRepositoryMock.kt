package com.koleychik.core_authentication.mockData

import com.koleychik.core_authentication.api.AccountRepository
import org.mockito.ArgumentMatchers
import org.mockito.Mockito

class AccountRepositoryMock {

    val accountRepository = Mockito.mock(AccountRepository::class.java)

    init {
        val checkPassword =
            Mockito.`when`(
                accountRepository.updatePassword(
                    ArgumentMatchers.any(String::class.java),
                    ArgumentMatchers.any())
                ).then {
//                    when (checkPassword(it.getArgument(0, String::class.java))){
////                        it.getArgument<>(1)()
//                    }
                }
    }

}