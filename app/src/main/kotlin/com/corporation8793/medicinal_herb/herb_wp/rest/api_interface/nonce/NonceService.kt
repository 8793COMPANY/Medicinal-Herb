package com.corporation8793.medicinal_herb.herb_wp.rest.api_interface.nonce

import com.corporation8793.medicinal_herb.herb_wp.rest.data.nonce.Nonce
import com.corporation8793.medicinal_herb.herb_wp.rest.data.nonce.SignUpStatus
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface NonceService {
    @POST("api/get_nonce/?controller=user&method=register")
    fun getNonce() : Call<Nonce>

    @POST("api/user/register/?insecure=cool&notify=no")
    fun runSignUp(@Query("nonce") nonce: String,
                  @Query("username") username: String,
                  @Query("user_pass") user_pass: String,
                  @Query("email") email: String,
                  @Query("display_name") display_name: String) : Call<SignUpStatus>
}