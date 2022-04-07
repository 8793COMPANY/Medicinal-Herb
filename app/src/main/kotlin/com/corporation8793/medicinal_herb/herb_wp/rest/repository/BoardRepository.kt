package com.corporation8793.medicinal_herb.herb_wp.rest.repository

import com.corporation8793.medicinal_herb.herb_wp.rest.RestClient
import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.Post
import okhttp3.Credentials

class BoardRepository(val basicAuth : String) {
    fun createPost(title : String, content : String) : String {
        // status : publish
        // categories : String
        // featured_media_id : String
        val call = RestClient.boardService.create(basicAuth, title = title, content = content)

        // for test (execute)
        return call.execute().code().toString()
    }

    fun retrieveOnePost(postId : String) : Pair<String, Post?> {
        val call = RestClient.boardService.retrieveOne(id = postId)

        val response = call.execute()

        // for test (execute)
        return Pair(response.code().toString(), response.body())
    }

    fun retrieveAllPost(per_page : String = "100", page : String = "1", order : String = "desc") : Pair<String, List<Post>?> {
        // 게시물 100개 = 페이지 1개
        // 기본값 : date 기준 내림차순 (최신 -> 오래된)
        val call = RestClient.boardService.retrieveAll(per_page, page, order)

        val response = call.execute()

        // for test (execute)
        return Pair(response.code().toString(), response.body())
    }

    fun retrieveCategoriesPost(per_page : String = "100", page : String = "1", order : String = "desc",
                               categories : String = "chitchat") : Pair<String, List<Post>?> {
        // 게시물 100개 = 페이지 1개
        // 정렬 기본값 : date 기준 내림차순 (최신 -> 오래된)
        // 카테고리 기본값 : Chitchat

        // 대, 소문자 구분안하고 입력해도 카테고리 컨버팅 가능
        var convertCategories = when (categories.lowercase()) {
            "event" -> "1"
            "recommend" -> "5"
            "dictionary" -> "6"
            "farm" -> "7"
            "qna" -> "8"
            "chitchat" -> "10"
            else -> {
                // 잘못된 카테고리 입력은 Chitchat 으로 처리
                "10"
            }
        }

        val call = RestClient.boardService.retrieveCategories(per_page, page, order, convertCategories)

        val response = call.execute()

        // for test (execute)
        return Pair(response.code().toString(), response.body())
    }

    fun retrieveSearchAll(per_page : String = "100", page : String = "1", order : String = "desc",
                          search : String) : Pair<String, List<Post>?> {
        // 게시물 100개 = 페이지 1개
        // 제목 검색
        // 정렬 기본값 : date 기준 내림차순 (최신 -> 오래된)

        val call = RestClient.boardService.retrieveSearchAll(per_page, page, order, search)

        val response = call.execute()

        // for test (execute)
        return Pair(response.code().toString(), response.body())
    }

    fun retrieveSearchInCategories(per_page : String = "100", page : String = "1", order : String = "desc",
                               categories : String = "chitchat",
                               search : String) : Pair<String, List<Post>?> {
        // 게시물 100개 = 페이지 1개
        // 제목 검색
        // 정렬 기본값 : date 기준 내림차순 (최신 -> 오래된)
        // 카테고리 기본값 : Chitchat

        // 대, 소문자 구분안하고 입력해도 카테고리 컨버팅 가능
        var convertCategories = when (categories.lowercase()) {
            "event" -> "1"
            "recommend" -> "5"
            "dictionary" -> "6"
            "farm" -> "7"
            "qna" -> "8"
            "chitchat" -> "10"
            else -> {
                // 잘못된 카테고리 입력은 Chitchat 으로 처리
                "10"
            }
        }

        val call = RestClient.boardService.retrieveSearchInCategories(per_page, page, order,
            convertCategories, search)

        val response = call.execute()

        // for test (execute)
        return Pair(response.code().toString(), response.body())
    }
}