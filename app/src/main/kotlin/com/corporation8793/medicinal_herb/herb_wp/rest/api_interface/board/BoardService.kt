package com.corporation8793.medicinal_herb.herb_wp.rest.api_interface.board

import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.Post
import com.corporation8793.medicinal_herb.herb_wp.rest.data.nonce.Nonce
import com.corporation8793.medicinal_herb.herb_wp.rest.data.nonce.SignUpStatus
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface BoardService {
    @POST("api/get_nonce/?controller=user&method=register")
    fun getNonce() : Call<Nonce>

    @POST("api/user/register/?insecure=cool&notify=no")
    fun runSignUp(@Query("nonce") nonce: String,
                  @Query("username") username: String,
                  @Query("user_pass") user_pass: String,
                  @Query("email") email: String,
                  @Query("display_name") display_name: String) : Call<SignUpStatus>

    @FormUrlEncoded
    @POST("wp-json/wp/v2/posts")
    fun create(@Header("Authorization") h1 : String,
               @Field("status") status : String = "publish",
               @Field("title") title : String,
               @Field("content") content : String,
               @Field("categories") categories : String = "10",
               @Field("featured_media") featured_media : String = "0") : Call<ResponseBody>

    @GET("wp-json/wp/v2/posts")
    fun retrieveOne(@Query("id") id : String) : Call<Post>

    @GET("wp-json/wp/v2/posts")
    fun retrieveAll() : Call<List<Post>>

    @FormUrlEncoded
    @POST("wp-json/wp/v2/posts")
    fun update(@Header("Authorization") h1 : String,
               @Query("id") id : String,
               @Field("status") status : String = "publish",
               @Field("title") title : String,
               @Field("content") content : String,
               @Field("categories") categories : String = "10",
               @Field("featured_media") featured_media : String = "0") : Call<ResponseBody>

    @DELETE("wp-json/wp/v2/posts/?force=true")
    fun delete(@Header("Authorization") h1 : String,
               @Query("id") id : String) : Call<ResponseBody>
}