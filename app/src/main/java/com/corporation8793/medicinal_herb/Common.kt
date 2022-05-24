package com.corporation8793.medicinal_herb

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.corporation8793.medicinal_herb.activity.LoginActivity
import com.corporation8793.medicinal_herb.activity.main.MainActivity2
import com.corporation8793.medicinal_herb.adapter.CommentAdapter
import com.corporation8793.medicinal_herb.dto.CommentItem
import com.corporation8793.medicinal_herb.herb_wp.rest.RestClient
import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.Comment
import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.User
import com.corporation8793.medicinal_herb.herb_wp.rest.repository.BoardRepository
import com.corporation8793.medicinal_herb.herb_wp.rest.repository.NonceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Credentials

class Common {
    fun replaceText(text : String) : String{
        val regex = Regex("&.*;")
        val matchResult: MatchResult? = regex.find(text)
//        println("match value: ${matchResult?.value}")
        var result = text
        matchResult?.groupValues?.forEach {
            Log.e("match value", it)
            result = result.replace(it,"")
        }

        return result.replace("<p>","").replace("</p>","")
                .replace("<ul>","").replace("</ul>","")
                .replace("<li>","").replace("</li>","")
                .replace("<br>","").replace("<br />","")
                .replace("<strong>","").replace("</strong>","")

    }

     fun showAccessTerms(context : Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_access_term)


        var webview = dialog.findViewById<WebView>(R.id.webview)
        webview.webViewClient = WebViewClient()
        webview.getSettings().setJavaScriptEnabled(true)
        webview.getSettings().getLoadsImagesAutomatically()
        webview.getSettings().getUseWideViewPort()
        webview.loadUrl("https://blog.naver.com/wisi8793/222738671066")
//
        dialog.show()
        dialog.findViewById<LinearLayout>(R.id.ok_btn).setOnClickListener {
            dialog.dismiss()
        }


        val display =(context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        val size = Point()
        display.getSize(size)

        // Generate custom width and height and
        // add to the dialog attributes
        // we multiplied the width and height by 0.5,
        // meaning reducing the size to 50%
        val mLayoutParams = WindowManager.LayoutParams()
        mLayoutParams.width = (size.x * 0.9f).toInt()
        mLayoutParams.height = (size.y * 0.9f).toInt()
//
        dialog.window?.attributes = mLayoutParams
    }

//    fun updateUser() {
//        val testId = MySharedPreferences(this).getString("id","hello")
//        val testPw = MySharedPreferences(this).getString("pw","1234")
//        val basicAuth = Credentials.basic(testId, testPw)
//        val boardRepository = BoardRepository(basicAuth)
//
//
//        println("====== UsersRU             ======")
//        println("------ isValid             ------")
//        try {
//            val isValid = boardRepository.validationUser()
//
//            println("retrieve User 닉네임(ID) : ${isValid.second?.name}(${isValid.second?.id})")
//            println("retrieve Status : ${isValid.first}\n")
//            println("retrieve url : ${isValid.second?.url}\n")
////            if(img_uri != null)
////                boardRepository.updateUser(isValid.second?.id,,introduction.text.toString())
//
//
//        } catch (e: Exception) {
//            Log.e("e", e.toString())
//
//            Log.e("e", e.message.toString())
//        }
//    }

    fun commentCountSetting(context : Context, count_text : TextView, count: Int){
        GlobalScope.launch(Dispatchers.Default) {

            GlobalScope.launch(Dispatchers.Main) {
                count_text.text = "댓글 " + count
                var content = count_text.text.toString()
                val spannableString: SpannableString = SpannableString(content)
                var start = 2

                val colorGreenSpan = ForegroundColorSpan(context.resources.getColor(R.color.green))

                spannableString.setSpan(colorGreenSpan, start, content.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                count_text.text = spannableString
            }
        }
    }

    fun dataSetting(context: Context, postId : String, datas : MutableList<CommentItem>, adapter:CommentAdapter, commentCount : TextView) {
        val testId = MySharedPreferences(context).getString("id","hello")
        val testPw = MySharedPreferences(context).getString("pw","1234")
        val basicAuth = Credentials.basic(testId, testPw)
        val boardRepository = BoardRepository(basicAuth)


        println("====== UsersRU             ======")
        println("------ isValid             ------")

        val isValid = boardRepository.validationUser()


//            val qna_posting : Call<List<Post>> = RestClient.boardService.retrievePostInCategories("100","1","desc", RestClient.CATEGORY_QNA)
        val comment: List<Comment>? = boardRepository.retrieveAllComment(postId,false).second
        Log.e("check", comment!!.size.toString())
        var repo =""

        commentCountSetting(context,commentCount,boardRepository.retrieveAllComment(postId,true)!!.second!!.size)

        datas.apply {
            comment.forEach {
//                    var response = it.featured_media
                Log.e("comment author",it.author)
                Log.e("comment post",it.content.rendered)

                val user: User? =  RestClient.boardService.retrieveUser(it.author).execute().body()
                var img = "0"
                if (user?.id != null) {
                    Log.e("check id", user!!.id)
                    Log.e("check name", user!!.name)
                    Log.e("check url", user!!.url)
                    Log.e("check", user!!.description)
                    img = user!!.url
                }
                if(img.trim().equals(""))
                    img = "0"

                add(CommentItem(img,it.author_name,it.content.rendered,it.date,0))

                val reply: List<Comment>? = boardRepository.retrieveAllReply(it.id).second

                reply!!.forEach {
                    var reply_user_img ="0"
                    val user: User? =  RestClient.boardService.retrieveUser(it.author).execute().body()
                    var img = "0"
                    if (user?.id != null) {
                        Log.e("check id", user!!.id)
                        Log.e("check name", user!!.name)
                        Log.e("check url", user!!.url)
                        Log.e("check", user!!.description)
                        reply_user_img = user!!.url
                    }
                    if(reply_user_img.trim().equals(""))
                        reply_user_img = "0"

                    Log.e("reply post",it.content.rendered)
                    add(CommentItem(reply_user_img,it.author_name,it.content.rendered,it.date,1))
                }






                Log.e("id", it.id)
                Log.e("id", it.content.rendered)
//                        Log.e("response", response.guid.rendered)

//                    add(CommentItem("0",it.author_name,it.content.rendered,it.date,0))


            }
            adapter.datas = datas
            GlobalScope.launch(Dispatchers.Main) {    // 2
                adapter.notifyDataSetChanged()

            }





        }



    }




}