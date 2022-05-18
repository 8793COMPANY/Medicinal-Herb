package com.corporation8793.medicinal_herb.herb_wp.rest

import com.corporation8793.medicinal_herb.herb_wp.rest.repository.BoardRepository
import okhttp3.Credentials
import org.junit.Assert
import org.junit.Test
import java.io.File

class CreatePostWithMedia {
    @Test
    fun media() {
        val testId = "john3"
        val testPw = "testjohn"
        val basicAuth = Credentials.basic(testId, testPw)
        val boardRepository = BoardRepository(basicAuth)
        var htmlContent = ""


        println("====== CreatePostWithMedia ======")



        println("------ Upload              ------")
        // test image (Lenna.png)
        val file = File("src/test/java/com/corporation8793/medicinal_herb/herb_wp/rest/Lenna.png")
        val responseMedia = boardRepository.uploadMedia(file)
        Assert.assertEquals("201", responseMedia.first)
        println("response Media URL : ${responseMedia.second?.guid?.rendered}")
        println("response Media ID : ${responseMedia.second?.id}")
        htmlContent += "<p><img src=\"${responseMedia.second?.guid?.rendered}\"></p>"



//        println("------ Create              ------")
//        var responseCode = boardRepository.createPost(
//            title = "CreatePostWithMedia",
//            content = htmlContent + "나는 부자가 되고 싶다?!",
//            categories = RestClient.CATEGORY_CHITCHAT,
//            featured_media = responseMedia.second?.id
//        )
//        Assert.assertEquals("201", responseCode)
//        println("response Code : $responseCode\n")



        println("------ Retrieve            ------")
        var retrieveMedia = boardRepository.retrieveMedia(responseMedia.second?.id)
        Assert.assertEquals("200", retrieveMedia.first)
//        println("retrieve Media URL : ${retrieveMedia.second?.guid?.rendered}")
//        println("retrieve Media ID : ${retrieveMedia.second?.id}")
        println("retrieve Status : ${retrieveMedia.first}\n")




        println("====== EndTest             ======")
    }
}