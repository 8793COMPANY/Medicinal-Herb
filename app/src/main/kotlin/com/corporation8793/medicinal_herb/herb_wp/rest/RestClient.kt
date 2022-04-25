package com.corporation8793.medicinal_herb.herb_wp.rest

import com.corporation8793.medicinal_herb.herb_wp.rest.api_interface.board.BoardService
import com.corporation8793.medicinal_herb.herb_wp.rest.api_interface.nonce.NonceService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * [Retrofit] 사용을 위한 [object] 클래스
 * @author  두동근
 */
object RestClient {
    /**
     * Static IP of AWS Server (산야초 마을)
     * @see     <a href="http://3.37.133.132/">산야초 마을</a>
     */
    private const val baseUrl = "http://3.37.133.132/"
    /**
     * 맞춤추천 카테고리
     * @see     <a href="http://3.37.133.132/category/recommend/">산야초 마을 - 맞춤추천</a>
     */
    const val CATEGORY_RECOMMEND = "5"
    /**
     * 약초사전 카테고리
     * @see     <a href="http://3.37.133.132/category/dictionary/">산야초 마을 - 약초사전</a>
     */
    const val CATEGORY_DICTIONARY = "6"
    /**
     * 방방곡곡 약초농장 카테고리
     * @see     <a href="http://3.37.133.132/category/farm/">산야초 마을 - 방방곡곡 약초농장</a>
     */
    const val CATEGORY_FARM = "7"
    /**
     * 궁금해요 카테고리
     * @see     <a href="http://3.37.133.132/category/qna/">산야초 마을 - 궁금해요</a>
     */
    const val CATEGORY_QNA = "8"
    /**
     * 이벤트 카테고리
     * @see     <a href="http://3.37.133.132/category/event/">산야초 마을 - 이벤트</a>
     */
    const val CATEGORY_EVENT = "9"
    /**
     * 약초수다 카테고리
     * @see     <a href="http://3.37.133.132/category/chitchat/">산야초 마을 - 약초수다</a>
     */
    const val CATEGORY_CHITCHAT = "10"
    /**
     * 진행중인 이벤트 카테고리
     * @see     <a href="http://3.37.133.132/category/event/ongoing/">산야초 마을 - 진행중인 이벤트</a>
     */
    const val CATEGORY_EVENT_ONGOING = "21"
    /**
     * 종료된 이벤트 카테고리
     * @see     <a href="http://3.37.133.132/category/event/done/">산야초 마을 - 종료된 이벤트</a>
     */
    const val CATEGORY_EVENT_DONE = "22"

    /**
     * 산야초 마을 정보로 초기화된 [Retrofit]
     */
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * [retrofit]의 [NonceService]
     */
    val nonceService = retrofit.create(NonceService::class.java)!!
    /**
     * [retrofit]의 [BoardService]
     */
    val boardService = retrofit.create(BoardService::class.java)!!
}