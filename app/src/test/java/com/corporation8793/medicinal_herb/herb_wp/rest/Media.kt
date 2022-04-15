package com.corporation8793.medicinal_herb.herb_wp.rest

import com.corporation8793.medicinal_herb.herb_wp.rest.repository.BoardRepository
import okhttp3.Credentials
import org.junit.Assert
import org.junit.Test
import java.io.File


class Media {
    @Test
    fun media() {
        val testId = "john3"
        val testPw = "testjohn"
        val basicAuth = Credentials.basic(testId, testPw)
        val boardRepository = BoardRepository(basicAuth)


        println("====== media    ======")



        println("------ Upload   ------")
        // test image (Lenna.png)
        val file = File("src/test/java/com/corporation8793/medicinal_herb/herb_wp/rest/Lenna.png")
        val responseMedia = boardRepository.uploadMedia(file)
        Assert.assertEquals("201", responseMedia.first)
        println("response Media URL : ${responseMedia.second?.guid?.rendered}")
        println("response Media ID : ${responseMedia.second?.id}")




        println("====== EndTest  ======")
    }
}