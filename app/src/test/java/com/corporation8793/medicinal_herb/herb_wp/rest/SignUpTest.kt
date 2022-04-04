package com.corporation8793.medicinal_herb.herb_wp.rest

import org.junit.Test

class SignUpTest {
    @Test
    fun signUp() {
        val restRepository = RestRepository()

        println("====== SignUpTest ======")
        println("------ getNonce() ------")

        restRepository.getNonce()
        println("nonce value : ${restRepository.nonce}")

        println("------ runSignUp() ------")

        restRepository.runSignUp("john7", "testjohn", "john6@gmail.com", "존식이")
        when(restRepository.signUpStatus.status) {
            "ok" -> println("runSignUp : ${restRepository.signUpStatus.status}, ${restRepository.signUpStatus.username}")
            "error" -> println("runSignUp : ${restRepository.signUpStatus.status}, ${restRepository.signUpStatus.error}")
        }

        println("====== EndTest    ======")
    }
}