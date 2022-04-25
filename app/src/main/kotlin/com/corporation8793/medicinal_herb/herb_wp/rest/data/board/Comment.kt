package com.corporation8793.medicinal_herb.herb_wp.rest.data.board

/**
 * 댓글 data class
 * * 댓글 : parent 0
 * * 대댓글 : parent {Comment ID}
 * @author  두동근
 * @param   id                  댓글의 고유 번호(Comment ID)
 * @param   post                댓글이 달릴 게시물의 고유 번호(Post ID)
 * @param   parent              댓글, 대댓글 설정 (기본값 : 댓글)
 * @param   author              댓글을 작성한 회원의 고유 번호(User ID)
 * @param   author_name         댓글을 작성한 회원의 별명(닉네임)
 * @param   date                댓글의 작성 일자
 * @param   content             [CommentContent] 클래스
 * @param   type                타입 (expected : "comment")
 */
data class Comment(val id : String,
                   val post : String,
                   val parent : String = "0",
                   val author : String,
                   val author_name : String,
                   val date : String,
                   val content : CommentContent,
                   val type : String)
/**
 * [Comment.content]
 * @author  두동근
 * @param   rendered    댓글의 내용
 */
data class CommentContent(val rendered : String)
