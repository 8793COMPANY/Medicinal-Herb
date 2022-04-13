package com.corporation8793.medicinal_herb.herb_wp.rest.repository

import com.corporation8793.medicinal_herb.herb_wp.rest.RestClient
import com.corporation8793.medicinal_herb.herb_wp.rest.api_interface.nonce.NonceService
import com.corporation8793.medicinal_herb.herb_wp.rest.data.nonce.SignUpStatus
import okhttp3.Credentials

/**
 * [NonceService]의 구현 클래스
 * @author  두동근
 * @see     NonceService
 * @see     <a href="https://wordpress.org/plugins/json-api-user/">WP-Plugin [JSON API User]</a>
 */
class NonceRepository {
    /**
     * WP용 암호화 임시값(Nonce)
     */
    var nonce = ""
    /**
     * 회원가입([runSignUp]) 결과값
     */
    var signUpStatus = SignUpStatus()

    /**
     * 회원가입([runSignUp])을 위한 [nonce]를 초기화합니다.
     * @author  두동근
     */
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

    /**
     * 입력받은 정보로 회원가입합니다.
     * @author  두동근
     * @param   id          회원 ID
     * @param   pw          회원 PASSWORD
     * @param   email       회원 E-MAIL
     * @param   nickname    회원 별명
     * @see     <a href="https://wordpress.org/plugins/json-api-user/#method%3A%20register">Method: register [JSON API User]</a>
     */
    fun runSignUp(id : String, pw : String, email : String, nickname : String) {
        val call = RestClient.nonceService.runSignUp(nonce = nonce, username = id, user_pass = pw, email = email, display_name = nickname)

        // for test (execute)
        signUpStatus = call.execute().body()!!.copy()
    }
}