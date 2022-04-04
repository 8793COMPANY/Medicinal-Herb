package com.corporation8793.medicinal_herb.herb_wp.rest

import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import com.corporation8793.medicinal_herb.herb_wp.rest.data.Nonce
import com.corporation8793.medicinal_herb.herb_wp.rest.data.SignUpStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestRepository {
    var nonce = ""
    var signUpStatus = SignUpStatus()

    fun getNonce() {
        val call = RestClient.service.getNonce()

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
        val call = RestClient.service.runSignUp(nonce = nonce, username = id, user_pass = pw, email = email, display_name = nickname)

        // for test (execute)
        signUpStatus = call.execute().body()!!.copy()
    }
}