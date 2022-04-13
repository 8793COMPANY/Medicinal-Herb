package com.corporation8793.medicinal_herb.herb_wp.rest.api_interface.board

import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.Comment
import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.Post
import com.corporation8793.medicinal_herb.herb_wp.rest.data.nonce.Nonce
import com.corporation8793.medicinal_herb.herb_wp.rest.data.nonce.SignUpStatus
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface BoardService {
    // Post
    @FormUrlEncoded
    @POST("wp-json/wp/v2/posts")
    fun createPost(@Header("Authorization") h1 : String,
               @Field("status") status : String = "publish",
               @Field("title") title : String,
               @Field("content") content : String,
               @Field("categories") categories : String = "10",
               @Field("featured_media") featured_media : String = "0") : Call<ResponseBody>

    @GET("wp-json/wp/v2/posts/{id}")
    fun retrieveOnePost(@Path("id") id : String) : Call<Post>

    @GET("wp-json/wp/v2/posts")
    fun retrieveAllPost(@Query("per_page") per_page : String = "100",
                    @Query("page") page : String = "1",
                    @Query("order") order : String = "desc") : Call<List<Post>>

    @GET("wp-json/wp/v2/posts")
    fun retrievePostInCategories(@Query("per_page") per_page : String = "100",
                           @Query("page") page : String = "1",
                           @Query("order") order : String = "desc",
                           @Query("categories") categories : String) : Call<List<Post>>

    @GET("wp-json/wp/v2/posts")
    fun retrieveAllPostAndSearch(@Query("per_page") per_page : String = "100",
                           @Query("page") page : String = "1",
                           @Query("order") order : String = "desc",
                           @Query("search") search : String) : Call<List<Post>>

    @GET("wp-json/wp/v2/posts")
    fun retrievePostInCategoriesAndSearch(@Query("per_page") per_page : String = "100",
                                   @Query("page") page : String = "1",
                                   @Query("order") order : String = "desc",
                                   @Query("categories") categories : String,
                                   @Query("search") search : String) : Call<List<Post>>

    @FormUrlEncoded
    @POST("wp-json/wp/v2/posts/{id}")
    fun updatePost(@Header("Authorization") h1 : String,
               @Path("id") id : String,
               @Field("status") status : String = "publish",
               @Field("title") title : String,
               @Field("content") content : String,
               @Field("categories") categories : String = "10",
               @Field("featured_media") featured_media : String = "0") : Call<ResponseBody>

    @DELETE("wp-json/wp/v2/posts/{id}?force=true")
    fun deletePost(@Header("Authorization") h1 : String,
               @Path("id") id : String) : Call<ResponseBody>



    // Comment
    @FormUrlEncoded
    @POST("wp-json/wp/v2/comments")
    fun createComment(@Header("Authorization") h1 : String,
                      @Field("post") post : String,
                      @Field("parent") parent : String = "0",
                      @Field("content") content : String) : Call<ResponseBody>

    @GET("wp-json/wp/v2/comments/{id}")
    fun retrieveOneComment(@Path("id") id : String) : Call<Comment>

    @GET("wp-json/wp/v2/comments")
    fun retrieveAllComment(@Query("post") post : String) : Call<List<Comment>>

    @FormUrlEncoded
    @POST("wp-json/wp/v2/comments/{id}")
    fun updateComment(@Header("Authorization") h1 : String,
                      @Path("id") id : String,
                      @Field("content") content : String) : Call<ResponseBody>

    @DELETE("wp-json/wp/v2/comments/{id}?force=true")
    fun deleteComment(@Header("Authorization") h1 : String,
                   @Path("id") id : String) : Call<ResponseBody>
}