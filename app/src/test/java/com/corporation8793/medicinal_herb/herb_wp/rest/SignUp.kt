package com.corporation8793.medicinal_herb.herb_wp.rest

import com.corporation8793.medicinal_herb.herb_wp.rest.repository.NonceRepository
import org.junit.Assert
import org.junit.Test

class SignUp {
    @Test
    fun signUp() {
        val nonceRepository = NonceRepository()

        println("====== SignUp     ======")
        println("------ getNonce() ------")

        nonceRepository.getNonce()
        Assert.assertNotEquals("", nonceRepository.nonce)
        println("nonce value : ${nonceRepository.nonce}")

        println("------ runSignUp() ------")

        nonceRepository.runSignUp("john8", "testjohn", "john8@gmail.com", "에잇존")
        Assert.assertEquals("ok", nonceRepository.signUpStatus.status)

        when(nonceRepository.signUpStatus.status) {
            "ok" -> println("runSignUp : ${nonceRepository.signUpStatus.status}, ${nonceRepository.signUpStatus.username}")
            "error" -> println("runSignUp : ${nonceRepository.signUpStatus.status}, ${nonceRepository.signUpStatus.error}")
        }

        println("====== EndTest    ======")
    }
}