package com.corporation8793.medicinal_herb.herb_wp.rest.api_interface.board

import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.Post
import com.corporation8793.medicinal_herb.herb_wp.rest.data.nonce.Nonce
import com.corporation8793.medicinal_herb.herb_wp.rest.data.nonce.SignUpStatus
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface BoardService {
    @FormUrlEncoded
    @POST("wp-json/wp/v2/posts")
    fun create(@Header("Authorization") h1 : String,
               @Field("status") status : String = "publish",
               @Field("title") title : String,
               @Field("content") content : String,
               @Field("categories") categories : String = "10",
               @Field("featured_media") featured_media : String = "0") : Call<ResponseBody>

    @GET("wp-json/wp/v2/posts/{id}")
    fun retrieveOne(@Path("id") id : String) : Call<Post>

    @GET("wp-json/wp/v2/posts")
    fun retrieveAll(@Query("per_page") per_page : String = "100",
                    @Query("page") page : String = "1",
                    @Query("order") order : String = "desc") : Call<List<Post>>

    @GET("wp-json/wp/v2/posts")
    fun retrieveCategories(@Query("per_page") per_page : String = "100",
                           @Query("page") page : String = "1",
                           @Query("order") order : String = "desc",
                           @Query("categories") categories : String) : Call<List<Post>>

    @GET("wp-json/wp/v2/posts")
    fun retrieveSearchAll(@Query("per_page") per_page : String = "100",
                           @Query("page") page : String = "1",
                           @Query("order") order : String = "desc",
                           @Query("search") search : String) : Call<List<Post>>

    @GET("wp-json/wp/v2/posts")
    fun retrieveSearchInCategories(@Query("per_page") per_page : String = "100",
                                   @Query("page") page : String = "1",
                                   @Query("order") order : String = "desc",
                                   @Query("categories") categories : String,
                                   @Query("search") search : String) : Call<List<Post>>

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