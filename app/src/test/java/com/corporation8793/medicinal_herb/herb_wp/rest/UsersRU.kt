package com.corporation8793.medicinal_herb.herb_wp.rest

import com.corporation8793.medicinal_herb.herb_wp.rest.repository.BoardRepository
import okhttp3.Credentials
import org.junit.Assert
import org.junit.Test
import java.io.File

class UsersRU {
    @Test
    fun media() {
        val testId = "john3"
        val testPw = "testjohn"
        val basicAuth = Credentials.basic(testId, testPw)
        val boardRepository = BoardRepository(basicAuth)


        println("====== UsersRU             ======")



        println("------ Retrieve            ------")
        var retrieveUser = boardRepository.retrieveUser("9")
        Assert.assertEquals("200", retrieveUser.first)
        println("retrieve User 닉네임(ID) : ${retrieveUser.second?.name}(${retrieveUser.second?.id})")
        println("retrieve User 한 줄 소개글 : ${retrieveUser.second?.description}")
        println("retrieve User URL : ${retrieveUser.second?.url}")
        println("retrieve Status : ${retrieveUser.first}\n")



        println("------ Update              ------")
        // test image (Lenna.png)
        val file = File("src/test/java/com/corporation8793/medicinal_herb/herb_wp/rest/Lenna.png")
        val responseMedia = boardRepository.uploadMedia(file)
        Assert.assertEquals("201", responseMedia.first)
        println("response Media URL : ${responseMedia.second?.guid?.rendered}")
        println("response Media ID : ${responseMedia.second?.id}")

        println("------ MediaUploadComplete ------")

        var updateUser = boardRepository.updateUser("9", responseMedia.second?.guid?.rendered, "현타가 너무 씨게 와서 힘든 존삼이")
        Assert.assertEquals("200", updateUser.first)
        println("update User 닉네임(ID) : ${updateUser.second?.name}(${updateUser.second?.id})")
        println("update User 한 줄 소개글 : ${updateUser.second?.description}")
        println("update User URL : ${updateUser.second?.url}")
        println("update Status : ${updateUser.first}\n")




        println("====== EndTest             ======")
    }
}