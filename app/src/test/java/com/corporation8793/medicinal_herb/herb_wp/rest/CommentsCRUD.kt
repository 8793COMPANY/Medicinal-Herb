package com.corporation8793.medicinal_herb.herb_wp.rest

import com.corporation8793.medicinal_herb.herb_wp.rest.repository.BoardRepository
import okhttp3.Credentials
import org.junit.Assert
import org.junit.Test

class CommentsCRUD {
    @Test
    fun postsCRUD() {
        val testId = "john3"
        val testPw = "testjohn"
        val basicAuth = Credentials.basic(testId, testPw)
        val boardRepository = BoardRepository(basicAuth)


        println("====== CommentsCRUD    ======")



//        println("------ Create          ------")
//        var responseCode = boardRepository.createComment("204", content = "댓글 ON~")
//        Assert.assertEquals("201", responseCode)
//        println("response Code : $responseCode\n")




        println("------ Retrieve        ------")



//        println("------ OneComment      ------")
//        val responseOneComment = boardRepository.retrieveOneComment("21")
//        Assert.assertEquals("200", responseOneComment.first)
//        if (responseOneComment.first == "200") {
//            println("response Comment : ${responseOneComment.second!!}\n")
//        }

        println("------ AllComment      ------")
        val responseAllComment = boardRepository.retrieveAllComment("144", false)
        Assert.assertEquals("200", responseAllComment.first)
        if (responseAllComment.first == "200") {
            for ((i, p) in responseAllComment.second!!.withIndex()) {
                println("response Comment $i : $p")
            }
        }
        println("------ AllReply        ------")
        val retrieveAllReply = boardRepository.retrieveAllReply("21")
        Assert.assertEquals("200", retrieveAllReply.first)
        if (retrieveAllReply.first == "200") {
            for ((i, p) in retrieveAllReply.second!!.withIndex()) {
                println("response Comment $i : $p")
            }
        }

//        println("------ Create c of c   ------")
//        var responseCode = boardRepository.createComment("204", "38", "대댓글 테스트 두둠칫~")
//        Assert.assertEquals("201", responseCode)
//        println("response Code : $responseCode\n")



//        println("------ Update          ------")
//        val updateComment = boardRepository.updateComment("22", "테스트 2번 댓글 수정이 된곤감??")
//        Assert.assertEquals("200", updateComment)
//        println("Update Comment : ${updateComment}\n")




//        println("------ Delete          ------")
//        val deleteComment = boardRepository.deleteComment("21")
//        Assert.assertEquals("200", deleteComment)
//        println("Delete Comment : ${deleteComment}\n")




        println("====== EndTest         ======")
    }
}