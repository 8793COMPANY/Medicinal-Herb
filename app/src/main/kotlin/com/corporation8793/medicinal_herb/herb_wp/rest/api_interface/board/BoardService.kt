package com.corporation8793.medicinal_herb.herb_wp.rest.api_interface.board

import com.corporation8793.medicinal_herb.herb_wp.rest.RestClient
import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.Comment
import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.Media
import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.Post
import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.User
import com.corporation8793.medicinal_herb.herb_wp.rest.data.nonce.Nonce
import com.corporation8793.medicinal_herb.herb_wp.rest.data.nonce.SignUpStatus
import okhttp3.Credentials
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File

/**
 * 게시판의 인터페이스
 * @author  두동근
 * @see     Credentials
 * @see     <a href="https://developer.wordpress.org/rest-api/reference/posts/">WP-Posts [REST API Reference]</a>
 * @see     <a href="https://developer.wordpress.org/rest-api/reference/comments/">WP-Comments [REST API Reference]</a>
 */
interface BoardService {
    // Post
    /**
     * 게시물을 생성합니다.
     * @author  두동근
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/posts/#create-a-post">Create a Post [REST API Reference]</a>
     */
    @FormUrlEncoded
    @POST("wp-json/wp/v2/posts")
    fun createPost(@Header("Authorization") h1 : String,
               @Field("status") status : String = "publish",
               @Field("title") title : String,
               @Field("content") content : String,
               @Field("categories") categories : String = "10",
               @Field("featured_media") featured_media : String? = "0") : Call<ResponseBody>
    /**
     * id([postId])가 일치하는 게시물을 검색합니다.
     * * (id가 일치하는 게시물은 1개(One)입니다.)
     * @author  두동근
     * @see     Post
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/posts/#retrieve-a-post">Retrieve a Post [REST API Reference]</a>
     */
    @GET("wp-json/wp/v2/posts/{id}")
    fun retrieveOnePost(@Path("id") id : String) : Call<Post>

    /**
     * 전체 게시물을 검색합니다.
     * @author  두동근
     * @see     Post
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/posts/#list-posts">List Posts [REST API Reference]</a>
     */
    @GET("wp-json/wp/v2/posts")
    fun retrieveAllPost(@Query("per_page") per_page : String = "100",
                    @Query("page") page : String = "1",
                    @Query("order") order : String = "desc") : Call<List<Post>>


    /**
     * 카테고리 내 전체 게시물을 검색합니다.
     * @author  두동근
     * @see     Post
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/posts/#list-posts">List Posts [REST API Reference]</a>
     */
    @GET("wp-json/wp/v2/posts")
    fun retrievePostInCategories(@Query("per_page") per_page : String = "100",
                           @Query("page") page : String = "1",
                           @Query("order") order : String = "desc",
                           @Query("categories") categories : String) : Call<List<Post>>

    /**
     * 전체 카테고리에서 [Post.title]이 [search]와 일치하는 게시물을 검색합니다.
     * @author  두동근
     * @see     Post
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/posts/#list-posts">List Posts [REST API Reference]</a>
     */
    @GET("wp-json/wp/v2/posts")
    fun retrieveAllPostAndSearch(@Query("per_page") per_page : String = "100",
                           @Query("page") page : String = "1",
                           @Query("order") order : String = "desc",
                           @Query("search") search : String) : Call<List<Post>>

    /**
     * [categories]카테고리에서 [Post.title]이 [search]와 일치하는 게시물을 검색합니다.
     * @author  두동근
     * @see     Post
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/posts/#list-posts">List Posts [REST API Reference]</a>
     */
    @GET("wp-json/wp/v2/posts")
    fun retrievePostInCategoriesAndSearch(@Query("per_page") per_page : String = "100",
                                   @Query("page") page : String = "1",
                                   @Query("order") order : String = "desc",
                                   @Query("categories") categories : String,
                                   @Query("search") search : String) : Call<List<Post>>

    /**
     * 게시물을 수정합니다.
     * * id([postId])가 일치하는 게시물을 수정합니다.
     * @author  두동근
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/posts/#update-a-post">Update a Post [REST API Reference]</a>
     */
    @FormUrlEncoded
    @POST("wp-json/wp/v2/posts/{id}")
    fun updatePost(@Header("Authorization") h1 : String,
               @Path("id") id : String,
               @Field("status") status : String = "publish",
               @Field("title") title : String,
               @Field("content") content : String,
               @Field("categories") categories : String = "10",
               @Field("featured_media") featured_media : String = "0") : Call<ResponseBody>

    /**
     * 게시물을 삭제합니다.
     * * id([postId])가 일치하는 게시물을 삭제합니다.
     * @author  두동근
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/posts/#delete-a-post">Delete a Post [REST API Reference]</a>
     */
    @DELETE("wp-json/wp/v2/posts/{id}?force=true")
    fun deletePost(@Header("Authorization") h1 : String,
               @Path("id") id : String) : Call<ResponseBody>



    // Comment
    /**
     * 댓글을 생성합니다.
     * * 댓글 : parent 0
     * * 대댓글 : parent {Comment ID}
     * @author  두동근
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/comments/#create-a-comment">Create a Comment [REST API Reference]</a>
     */
    @FormUrlEncoded
    @POST("wp-json/wp/v2/comments")
    fun createComment(@Header("Authorization") h1 : String,
                      @Field("post") post : String,
                      @Field("parent") parent : String = "0",
                      @Field("content") content : String) : Call<ResponseBody>

    /**
     * id([commentId])가 일치하는 댓글을 검색합니다.
     * * (id가 일치하는 댓글은 1개(One)입니다.)
     * @author  두동근
     * @see     Comment
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/comments/#retrieve-a-comment">Retrieve a Comment [REST API Reference]</a>
     */
    @GET("wp-json/wp/v2/comments/{id}")
    fun retrieveOneComment(@Path("id") id : String) : Call<Comment>

    /**
     * id([postId])가 일치하는 게시물의 모든 댓글[Comment]을 검색합니다.
     * @author  두동근
     * @see     Comment
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/comments/#list-comments">List Comments [REST API Reference]</a>
     */
    @GET("wp-json/wp/v2/comments")
    fun retrieveAllComment(@Query("post") post : String) : Call<List<Comment>>

    /**
     * 댓글을 수정합니다.
     * * id([commentId])가 일치하는 댓글을 수정합니다.
     * @author  두동근
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/comments/#update-a-comment">Update a Comment [REST API Reference]</a>
     */
    @FormUrlEncoded
    @POST("wp-json/wp/v2/comments/{id}")
    fun updateComment(@Header("Authorization") h1 : String,
                      @Path("id") id : String,
                      @Field("content") content : String) : Call<ResponseBody>

    /**
     * 댓글을 삭제합니다.
     * * id([commentId])가 일치하는 댓글을 삭제합니다.
     * @author  두동근
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/comments/#delete-a-comment">Delete a Comment [REST API Reference]</a>
     */
    @DELETE("wp-json/wp/v2/comments/{id}?force=true")
    fun deleteComment(@Header("Authorization") h1 : String,
                   @Path("id") id : String) : Call<ResponseBody>



    // Media
    /**
     * [Post.featured_media]로 사용할 이미지를 업로드합니다.
     * @author  두동근
     * @see     Media
     * @see     Pair
     * @see     File
     * @see     Post.featured_media
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/media/#create-a-media-item">Create a Media Item [REST API Reference]</a>
     */
    @Multipart
    @POST("http://3.37.133.132/wp-json/wp/v2/media")
    fun uploadMedia(@Header("Authorization") h1 : String,
                    @Part file : MultipartBody.Part) : Call<Media>
    /**
     * id([mediaId])가 일치하는 미디어를 검색합니다.
     * * (id가 일치하는 미디어는 1개(One)입니다.)
     * @author  두동근
     * @see     Media
     * @see     Pair
     * @see     Post.featured_media
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/media/#retrieve-a-media-item">Retrieve a Media Item [REST API Reference]</a>
     */
    @GET("http://3.37.133.132/wp-json/wp/v2/media/{id}")
    fun retrieveMedia(@Path("id") id : String?) : Call<Array<Media>>



    // User
    /**
     * id([userId])가 일치하는 유저를 검색합니다.
     * * (id가 일치하는 유저는 1개(One)입니다.)
     * @author  두동근
     * @see     User
     * @see     Pair
     * @see     Post.author
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/users/#retrieve-a-user">Retrieve a User [REST API Reference]</a>
     */
    @GET("http://3.37.133.132/wp-json/wp/v2/users/{id}")
    fun retrieveUser(@Path("id") id : String?) : Call<User>

    /**
     * 유저를 검증합니다.
     * @author  두동근
     * @see     User
     * @see     Pair
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/users/#retrieve-a-user-2">Retrieve a User [REST API Reference]</a>
     */
    @GET("http://3.37.133.132/wp-json/wp/v2/users/me")
    fun validationUser(@Header("Authorization") h1 : String) : Call<User>

    /**
     * 유저를 수정합니다.
     * * id([userId])가 일치하는 유저를 수정합니다.
     * @author  두동근
     * @see     User
     * @see     Pair
     * @see     Post.author
     * @see     Comment.author
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/users/#update-a-user">Update a User [REST API Reference]</a>
     */
    @FormUrlEncoded
    @POST("http://3.37.133.132/wp-json/wp/v2/users/{id}")
    fun updateUser(@Header("Authorization") h1 : String,
                     @Path("id") id : String?,
                     @Field("url") url : String?,
                     @Field("description") description : String?) : Call<User>
}