package com.corporation8793.medicinal_herb.herb_wp.rest.data.board

/**
 * 회원 data class
 * @author  두동근
 * @param   id                  회원의 고유 번호(User ID)
 * @param   name                회원의 별명(닉네임)
 * @param   url                 프로필 이미지의 HTTP URL
 * @param   description         회원 소개글
 */
data class User(val id : String,
                val name : String,
                val url : String,
                val description : String)