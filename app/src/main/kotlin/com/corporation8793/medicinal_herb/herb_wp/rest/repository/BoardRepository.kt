package com.corporation8793.medicinal_herb.herb_wp.rest.repository

import android.app.Application
import com.corporation8793.medicinal_herb.herb_wp.rest.RestClient
import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.Post
import com.corporation8793.medicinal_herb.herb_wp.rest.api_interface.board.BoardService
import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.Comment
import okhttp3.Credentials

/**
 * [BoardService]의 구현 클래스
 * @author  두동근
 * @param   basicAuth [Credentials.basic]으로 생성된 basicAuth 값
 * @see     BoardService
 * @see     Credentials
 * @see     <a href="https://developer.wordpress.org/rest-api/reference/posts/">WP-Posts [REST API Reference]</a>
 */
class BoardRepository(val basicAuth : String) {
    // Post
    /**
     * 게시물을 생성합니다.
     * @author  두동근
     * @param   title   제목
     * @param   content 내용
     * @return  responseCode (expected : "201")
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/posts/#create-a-post">Create a Post [REST API Reference]</a>
     */
    fun createPost(title : String, content : String) : String {
        // status : publish
        // categories : String
        // featured_media_id : String
        val call = RestClient.boardService.createPost(basicAuth, title = title, content = content)

        // for test (execute)
        return call.execute().code().toString()
    }

    /**
     * id([postId])가 일치하는 게시물을 검색합니다.
     * * (id가 일치하는 게시물은 1개(One)입니다.)
     * @author  두동근
     * @param   postId  게시물 id
     * @return  responseCode (expected : "200"), [Post]
     * @see     Post
     * @see     Pair
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/posts/#retrieve-a-post">Retrieve a Post [REST API Reference]</a>
     */
    fun retrieveOnePost(postId : String) : Pair<String, Post?> {
        val call = RestClient.boardService.retrieveOnePost(id = postId)

        val response = call.execute()

        // for test (execute)
        return Pair(response.code().toString(), response.body())
    }

    /**
     * 전체 게시물을 검색합니다.
     * @author  두동근
     * @param   per_page    페이지 당 게시물 개수 (기본값 : 100)
     * @param   page        선택한 페이지 (기본값 : 1)
     * @param   order       [Post.date]기준 내림차순(desc), 오름차순(asc) (기본값 : desc)
     * @return  responseCode (expected : "200"), [List<Post>]
     * @see     Post
     * @see     Pair
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/posts/#list-posts">List Posts [REST API Reference]</a>
     */
    fun retrieveAllPost(per_page : String = "100", page : String = "1", order : String = "desc") : Pair<String, List<Post>?> {
        val call = RestClient.boardService.retrieveAllPost(per_page, page, order)

        val response = call.execute()

        // for test (execute)
        return Pair(response.code().toString(), response.body())
    }

    /**
     * 카테고리 내 전체 게시물을 검색합니다.
     * @author  두동근
     * @param   per_page    페이지 당 게시물 개수 (기본값 : 100)
     * @param   page        선택한 페이지 (기본값 : 1)
     * @param   order       [Post.date]기준 내림차순(desc), 오름차순(asc) (기본값 : desc)
     * @param   categories  [RestClient]의 카테고리값 (기본값 : [RestClient.CATEGORY_CHITCHAT])
     * * 이벤트 - [RestClient.CATEGORY_EVENT]
     * * 맞춤추천 - [RestClient.CATEGORY_RECOMMEND]
     * * 약초사전 - [RestClient.CATEGORY_DICTIONARY]
     * * 방방곡곡 약초농장 - [RestClient.CATEGORY_FARM]
     * * 궁금해요 - [RestClient.CATEGORY_QNA]
     * * 약초수다 - [RestClient.CATEGORY_CHITCHAT]
     *
     * @return  responseCode (expected : "200"), [List<Post>]
     * @see     Post
     * @see     Pair
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/posts/#list-posts">List Posts [REST API Reference]</a>
     */
    fun retrievePostInCategories(per_page : String = "100", page : String = "1", order : String = "desc",
                               categories : String = RestClient.CATEGORY_CHITCHAT) : Pair<String, List<Post>?> {
        val verifiedCategories = when (categories) {
            "1", "5", "6", "7", "8", "10" -> categories
            else -> {
                // 잘못된 카테고리 입력은 Chitchat 으로 처리
                RestClient.CATEGORY_CHITCHAT
            }
        }

        val call = RestClient.boardService.retrievePostInCategories(per_page, page, order, verifiedCategories)

        val response = call.execute()

        // for test (execute)
        return Pair(response.code().toString(), response.body())
    }

    /**
     * 전체 카테고리에서 [Post.title]이 [search]와 일치하는 게시물을 검색합니다.
     * @author  두동근
     * @param   per_page    페이지 당 게시물 개수 (기본값 : 100)
     * @param   page        선택한 페이지 (기본값 : 1)
     * @param   order       [Post.date]기준 내림차순(desc), 오름차순(asc) (기본값 : desc)
     * @param   search      검색 키워드
     * @return  responseCode (expected : "200"), [List<Post>]
     * @see     Post
     * @see     Pair
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/posts/#list-posts">List Posts [REST API Reference]</a>
     */
    fun retrieveAllPostAndSearch(per_page : String = "100", page : String = "1", order : String = "desc",
                          search : String) : Pair<String, List<Post>?> {
        val call = RestClient.boardService.retrieveAllPostAndSearch(per_page, page, order, search)

        val response = call.execute()

        // for test (execute)
        return Pair(response.code().toString(), response.body())
    }

    /**
     * [categories]카테고리에서 [Post.title]이 [search]와 일치하는 게시물을 검색합니다.
     * @author  두동근
     * @param   per_page    페이지 당 게시물 개수 (기본값 : 100)
     * @param   page        선택한 페이지 (기본값 : 1)
     * @param   order       [Post.date]기준 내림차순(desc), 오름차순(asc) (기본값 : desc)
     * @param   categories  [RestClient]의 카테고리값 (기본값 : [RestClient.CATEGORY_CHITCHAT])
     * * 이벤트 - [RestClient.CATEGORY_EVENT]
     * * 맞춤추천 - [RestClient.CATEGORY_RECOMMEND]
     * * 약초사전 - [RestClient.CATEGORY_DICTIONARY]
     * * 방방곡곡 약초농장 - [RestClient.CATEGORY_FARM]
     * * 궁금해요 - [RestClient.CATEGORY_QNA]
     * * 약초수다 - [RestClient.CATEGORY_CHITCHAT]
     *
     * @param   search      검색 키워드
     * @return  responseCode (expected : "200"), [List<Post>]
     * @see     Post
     * @see     Pair
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/posts/#list-posts">List Posts [REST API Reference]</a>
     */
    fun retrievePostInCategoriesAndSearch(per_page : String = "100", page : String = "1", order : String = "desc",
                               categories : String = RestClient.CATEGORY_CHITCHAT,
                               search : String) : Pair<String, List<Post>?> {
        val verifiedCategories = when (categories) {
            "1", "5", "6", "7", "8", "10" -> categories
            else -> {
                // 잘못된 카테고리 입력은 Chitchat 으로 처리
                RestClient.CATEGORY_CHITCHAT
            }
        }

        val call = RestClient.boardService.retrievePostInCategoriesAndSearch(per_page, page, order,
            verifiedCategories, search)

        val response = call.execute()

        // for test (execute)
        return Pair(response.code().toString(), response.body())
    }
    /**
     * 게시물을 수정합니다.
     * * id([postId])가 일치하는 게시물을 수정합니다.
     * @author  두동근
     * @param   postId  게시물 id
     * @param   title   제목
     * @param   content 내용
     * @return  responseCode (expected : "200")
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/posts/#update-a-post">Update a Post [REST API Reference]</a>
     */
    fun updatePost(postId : String, title : String, content : String) : String {
        val call = RestClient.boardService.updatePost(basicAuth, id = postId, title = title, content = content)

        // for test (execute)
        return call.execute().code().toString()
    }
    /**
     * 게시물을 삭제합니다.
     * * id([postId])가 일치하는 게시물을 삭제합니다.
     * @author  두동근
     * @param   postId  게시물 id
     * @return  responseCode (expected : "200")
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/posts/#delete-a-post">Delete a Post [REST API Reference]</a>
     */
    fun deletePost(postId : String) : String {
        val call = RestClient.boardService.deletePost(basicAuth, id = postId)

        // for test (execute)
        return call.execute().code().toString()
    }



    // Comment
    /**
     * 댓글을 생성합니다.
     * * 댓글 : parent 0
     * * 대댓글 : parent {Comment ID}
     * @author  두동근
     * @param   postId      댓글이 달릴 게시물의 고유 번호(Post ID)
     * @param   parent      댓글, 대댓글 설정 (기본값 : 댓글)
     * @param   content     내용
     * @return  responseCode (expected : "201")
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/comments/#create-a-comment">Create a Comment [REST API Reference]</a>
     */
    fun createComment(postId : String, parent : String = "0", content : String) : String =
        RestClient.boardService.createComment(basicAuth, postId, parent, content)
            .execute().code().toString()
    /**
     * id([commentId])가 일치하는 댓글을 검색합니다.
     * * (id가 일치하는 댓글은 1개(One)입니다.)
     * @author  두동근
     * @param   commentId  댓글 id
     * @return  responseCode (expected : "200"), [Comment]
     * @see     Comment
     * @see     Pair
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/comments/#retrieve-a-comment">Retrieve a Comment [REST API Reference]</a>
     */
    fun retrieveOneComment(commentId : String) : Pair<String, Comment?> {
        val response = RestClient.boardService.retrieveOneComment(commentId).execute()

        return Pair(response.code().toString(), response.body())
    }
    /**
     * id([postId])가 일치하는 게시물의 모든 댓글[Comment]을 검색합니다.
     * @author  두동근
     * @param   postId  게시물 id
     * @return  responseCode (expected : "200"), [List<Comment>]
     * @see     Comment
     * @see     Pair
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/comments/#list-comments">List Comments [REST API Reference]</a>
     */
    fun retrieveAllComment(postId : String) : Pair<String, List<Comment>?> {
        val response = RestClient.boardService.retrieveAllComment(postId).execute()

        return Pair(response.code().toString(), response.body())
    }
    /**
     * 댓글을 수정합니다.
     * * id([commentId])가 일치하는 댓글을 수정합니다.
     * @author  두동근
     * @param   commentId   댓글 id
     * @param   content     내용
     * @return  responseCode (expected : "200")
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/comments/#update-a-comment">Update a Comment [REST API Reference]</a>
     */
    fun updateComment(commentId : String, content : String) : String =
        RestClient.boardService.updateComment(basicAuth, commentId, content)
            .execute().code().toString()
    /**
     * 댓글을 삭제합니다.
     * * id([commentId])가 일치하는 댓글을 삭제합니다.
     * @author  두동근
     * @param   commentId  댓글 id
     * @return  responseCode (expected : "200")
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/comments/#delete-a-comment">Delete a Comment [REST API Reference]</a>
     */
    fun deleteComment(commentId : String) : String =
        RestClient.boardService.deleteComment(basicAuth, commentId)
            .execute().code().toString()
}