package com.corporation8793.medicinal_herb.herb_wp.rest.data.board

/**
 * 게시물 data class
 * @author  두동근
 * @param   id                  게시물의 고유 번호(Post ID)
 * @param   date                게시물의 작성 일자
 * @param   type                게시물의 타입
 * @param   title               [Title] 클래스
 * @param   content             [PostContent] 클래스
 * @param   excerpt             [Excerpt] 클래스
 * @param   author              게시물을 작성한 회원
 * @param   featured_media      게시물의 대표 사진 리소스 ID값
 * @param   categories          게시물이 속한 카테고리
 * @param   tags                게시물의 태그
 */
data class Post(val id : String,
                val date : String,
                val type : String,
                val title : Title,
                val content : PostContent,
                val excerpt : Excerpt,
                val author : String,
                val featured_media : String,
                val categories : Array<String>,
                val tags : Array<String>)

/**
 * [Post.title]
 * @author  두동근
 * @param   rendered    게시물의 제목
 */
data class Title(val rendered : String)
/**
 * [Post.content]
 * @author  두동근
 * @param   rendered    게시물의 내용
 */
data class PostContent(val rendered : String)
/**
 * [Post.excerpt]
 * @author  두동근
 * @param   rendered    게시물의 요약 내용
 */
data class Excerpt(val rendered : String)