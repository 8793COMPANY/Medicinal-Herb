package com.corporation8793.medicinal_herb.herb_wp.rest.api_interface.nonce

import com.corporation8793.medicinal_herb.herb_wp.rest.data.nonce.Nonce
import com.corporation8793.medicinal_herb.herb_wp.rest.data.nonce.SignUpStatus
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Nonce, 회원가입의 인터페이스
 * @author  두동근
 * @see     <a href="https://wordpress.org/plugins/json-api-user/">WP-Plugin [JSON API User]</a>
 */
interface NonceService {
    /**
     * 회원가입([runSignUp])을 위한 [nonce]를 초기화합니다.
     * @author  두동근
     */
    @POST("api/get_nonce/?controller=user&method=register")
    fun getNonce() : Call<Nonce>

    /**
     * 입력받은 정보로 회원가입합니다.
     * @author  두동근
     * @see     <a href="https://wordpress.org/plugins/json-api-user/#method%3A%20register">Method: register [JSON API User]</a>
     */
    @POST("api/user/register/?insecure=cool&notify=no")
    fun runSignUp(@Query("nonce") nonce: String,
                  @Query("username") username: String,
                  @Query("user_pass") user_pass: String,
                  @Query("email") email: String,
                  @Query("display_name") display_name: String) : Call<SignUpStatus>
}