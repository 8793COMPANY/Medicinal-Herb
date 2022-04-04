package com.corporation8793.medicinal_herb.herb_wp.rest

import com.corporation8793.medicinal_herb.herb_wp.rest.repository.BoardRepository
import okhttp3.Credentials
import org.junit.Assert
import org.junit.Test

class PostsCRUD {
    //Assert.assertNotEquals("", nonceRepository.nonce)
    //Assert.assertEquals("201", responseCode)

    @Test
    fun postsCRUD() {
        val testId = "john3"
        val testPw = "testjohn"
        val basicAuth = Credentials.basic(testId, testPw)
        val boardRepository = BoardRepository(basicAuth)

        println("====== postsCRUD  ======")
        println("------ Create     ------")
        var responseCode = boardRepository.createPost("연동 테스트", "아아 마이크 테스트 ㅎㅎ")
        Assert.assertEquals("201", responseCode)
        println("response Code : $responseCode")

        println("====== EndTest    ======")
    }
}