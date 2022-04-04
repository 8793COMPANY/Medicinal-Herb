package com.corporation8793.medicinal_herb.herb_wp.rest.repository

import com.corporation8793.medicinal_herb.herb_wp.rest.RestClient
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
}