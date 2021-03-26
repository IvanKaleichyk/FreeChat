package com.koleychik.core_authorization

import com.koleychik.core_authorization.result.CheckResult
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class TestCheckData {

    @Test
    fun testPassword() {
        val passwordTrue1 = "qwerrtty"
        val passwordTrue2 = "qwerrtty94u9898"
        val passwordTrue3 = "q2e2y"
        val passwordFalse1 = "qwer"
        val passwordFalse2 = "qwerjojdfvojdfovjdfovjdfovjdfojv"
        assertThat(checkPassword(passwordTrue1) is CheckResult.Successful).isTrue
        assertThat(checkPassword(passwordTrue2) is CheckResult.Successful).isTrue
        assertThat(checkPassword(passwordTrue3) is CheckResult.Successful).isTrue

        val res1 = checkPassword(passwordFalse1)
        if (res1 is CheckResult.DataError)
            assertThat(res1.message).isEqualTo(R.string.password_is_too_short)
        val res2 = checkPassword(passwordFalse2)
        if (res2 is CheckResult.DataError)
            assertThat(res2.message).isEqualTo(R.string.password_is_too_long)
    }

    @Test
    fun testEmail() {
        val emailTrue1 = "hirhihir@ibot.com"
        val emailTrue2 = "hi2h34434ir@gmail.com"
        val emailTrue3 = "34ir@ibot.ru"

        val emailFalse1 = "34iribot.ru"
        val emailFalse2 = "34fbfbkbjk@otru"
        val emailFalse3 = "34iribotru"

        assertThat(checkEmail(emailTrue1) is CheckResult.Successful).isTrue
        assertThat(checkEmail(emailTrue2) is CheckResult.Successful).isTrue
        assertThat(checkEmail(emailTrue3) is CheckResult.Successful).isTrue


        val res1 = checkEmail(emailFalse1)
        if (res1 is CheckResult.DataError)
            assertThat(res1.message).isEqualTo(R.string.write_your_email)

        val res2 = checkEmail(emailFalse2)
        if (res2 is CheckResult.DataError)
            assertThat(res2.message).isEqualTo(R.string.write_your_email)

        val res3 = checkEmail(emailFalse3)
        if (res3 is CheckResult.DataError)
            assertThat(res3.message).isEqualTo(R.string.write_your_email)
    }
}
