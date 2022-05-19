package com.corporation8793.medicinal_herb.herb_wp.rest

import com.corporation8793.medicinal_herb.herb_wp.rest.repository.BoardRepository
import okhttp3.Credentials
import org.junit.Assert
import org.junit.Test

class PostsCRUD {
    @Test
    fun postsCRUD() {
        val testId = "john3"
        val testPw = "testjohn"
        val basicAuth = Credentials.basic(testId, testPw)
        val boardRepository = BoardRepository(basicAuth)

        println("====== postsCRUD       ======")



//        println("------ Create          ------")
//        var responseCode = boardRepository.createPost("테스트 1", "이벤트 게시판에 연동 게시물 작성", RestClient.CATEGORY_EVENT)
//        Assert.assertEquals("201", responseCode)
//        println("response Code : $responseCode\n")
//        responseCode = boardRepository.createPost("테스트 2", "진행중인 이벤트 게시판에 연동 게시물 작성", RestClient.CATEGORY_EVENT_ONGOING)
//        Assert.assertEquals("201", responseCode)
//        println("response Code : $responseCode\n")
//        responseCode = boardRepository.createPost("테스트 3", "종료된 이벤트 게시판에 연동 게시물 작성", RestClient.CATEGORY_EVENT_DONE)
//        Assert.assertEquals("201", responseCode)
//        println("response Code : $responseCode\n")




        println("------ Retrieve        ------")



        //println("------ OnePost         ------")
        println("------ EventPost       ------")
        val responseOnePost = boardRepository.retrieveOnePost("144")
        Assert.assertEquals("200", responseOnePost.first)
        if (responseOnePost.first == "200") {
            println("response Post : ${responseOnePost.second!!}\n")
            //println("Post Date : ${responseOnePost.second!!.date}\n")
            println("Owner Name : ${responseOnePost.second!!.acf.owner_name}\n")
        }

//        println("------ AllPost         ------")
//        val responseAllPost = boardRepository.retrieveAllPost()
//        Assert.assertEquals("200", responseAllPost.first)
//        if (responseAllPost.first == "200") {
//            for ((i, p) in responseAllPost.second!!.withIndex()) {
//                println("response Post $i : ${p.title.rendered}\n")
//            }
//        }
//
//
//
//
//        println("------ Update          ------")
//        val updatePost = boardRepository.updatePost("201", "수정이 된곤감??", "그렇다고하자~ ㅋ")
//        Assert.assertEquals("200", updatePost)
//        println("Update Post : ${updatePost}\n")
//
//
//
//
//        println("------ Delete          ------")
//        val deletePost = boardRepository.deletePost("201")
//        Assert.assertEquals("200", deletePost)
//        println("Delete Post : ${deletePost}\n")




        println("====== EndTest         ======")
    }
}