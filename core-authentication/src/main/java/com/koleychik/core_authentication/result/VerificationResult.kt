package com.koleychik.core_authentication.result

sealed class VerificationResult {
    object Successful : VerificationResult()
    object Waiting : VerificationResult()
    class DataError(val message: Int) : VerificationResult()
    class ServerError(val message: String) : VerificationResult()
}
