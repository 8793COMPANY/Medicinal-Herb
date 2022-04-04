package com.corporation8793.medicinal_herb.herb_wp.rest

import com.corporation8793.medicinal_herb.herb_wp.rest.api_interface.board.BoardService
import com.corporation8793.medicinal_herb.herb_wp.rest.api_interface.nonce.NonceService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestClient {
    private const val baseUrl = "http://3.37.133.132/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val nonceService = retrofit.create(NonceService::class.java)!!
    val boardService = retrofit.create(BoardService::class.java)!!
}