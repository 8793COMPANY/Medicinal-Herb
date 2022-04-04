package com.corporation8793.medicinal_herb.herb_wp.rest.repository

import com.corporation8793.medicinal_herb.herb_wp.rest.RestClient
import com.corporation8793.medicinal_herb.herb_wp.rest.data.nonce.SignUpStatus

class NonceRepository {
    var nonce = ""
    var signUpStatus = SignUpStatus()

    fun getNonce() {
        val call = RestClient.nonceService.getNonce()

        // for test (execute)
        nonce = call.execute().body()!!.nonce

        // for real (enqueue)
//        call.enqueue(object : Callback<Nonce> {
//            override fun onResponse(
//                call: Call<Nonce>,
//                response: Response<Nonce>
//            ) {
//                if(response.isSuccessful){
//                    System.out.println("onResponse")
//                    println("onResponse: ${response.body()!!.nonce}")
//                    nonce = response.body()!!.nonce
//                } else {
//                    println("onResponse: ${response.code()}")
//                }
//            }
//
//            override fun onFailure(call: Call<Nonce>, t: Throwable) {
//                println("onFailure: $t")
//            }
//        })
    }

    fun runSignUp(id : String, pw : String, email : String, nickname : String) {
        val call = RestClient.nonceService.runSignUp(nonce = nonce, username = id, user_pass = pw, email = email, display_name = nickname)

        // for test (execute)
        signUpStatus = call.execute().body()!!.copy()
    }
}