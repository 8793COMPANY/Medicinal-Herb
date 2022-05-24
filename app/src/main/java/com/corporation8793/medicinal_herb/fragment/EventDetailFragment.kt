package com.corporation8793.medicinal_herb.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.corporation8793.medicinal_herb.Common
import com.corporation8793.medicinal_herb.decoration.EventDecoration
import com.corporation8793.medicinal_herb.R
import com.corporation8793.medicinal_herb.activity.main.MainActivity2
import com.corporation8793.medicinal_herb.adapter.CommentAdapter
import com.corporation8793.medicinal_herb.dto.CommentItem
import com.corporation8793.medicinal_herb.dto.EventItem
import com.corporation8793.medicinal_herb.herb_wp.rest.RestClient
import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.Comment
import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.Post
import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.User
import com.corporation8793.medicinal_herb.herb_wp.rest.repository.BoardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EventListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EventDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var commentAdapter: CommentAdapter
    val datas = mutableListOf<CommentItem>()
    lateinit var divider : EventDecoration
    lateinit var comment_count : TextView

    val comment_list = mutableMapOf<String,Array<Int>>()

    var count = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_event_detail, container, false)
        val event_list = view.findViewById<RecyclerView>(R.id.event_list)
        comment_count = view.findViewById<TextView>(R.id.comment_count)

        var content = comment_count.text.toString()
        val spannableString : SpannableString = SpannableString(content)
        var start = 2

        val colorGreenSpan = ForegroundColorSpan(resources.getColor(R.color.green))

        spannableString.setSpan(colorGreenSpan,start,content.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        comment_count.text = spannableString

        val display : DisplayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(display)
        val height : Int =  (display.heightPixels / 8.5).toInt()

        commentAdapter = CommentAdapter(context,height);
        event_list.adapter = commentAdapter

        val lm = LinearLayoutManager(context)
        event_list.layoutManager = lm


        divider = EventDecoration(10)


        event_list.addItemDecoration(divider)
        Log.e("id",arguments?.getString("id","147").toString())

//        val one_posting : Call<List<Comment>> = RestClient.boardService.retrieveAllComment(arguments?.getString("id","147").toString())
        commentSetting()

//        GlobalScope.launch(Dispatchers.Default) {
//
////            val qna_posting : Call<List<Post>> = RestClient.boardService.retrievePostInCategories("100","1","desc", RestClient.CATEGORY_QNA)
//            val check: List<Comment>? = RestClient.boardService.retrieveAllComment(arguments?.getString("id","147").toString()).execute().body()!!
//            Log.e("check", check!!.size.toString())
//            var repo =""
//
//
//            datas.apply {
//                check.forEach {
////                    var response = it.featured_media
//                    Log.e("it.author",it.author)
//                    val check: User? =  RestClient.boardService.retrieveUser(it.author).execute().body()
//                    Log.e("check id",check!!.id)
//                    Log.e("check name",check!!.name)
//                    Log.e("check url",check!!.url)
//                    Log.e("check",check!!.description)
//                    var img = check!!.url
//                    if(img.trim().equals(""))
//                        img = "0"
//
//                    var replys = RestClient.boardService.retrieveAllReply(it.id).execute().body()!!
//                    replys.forEach{
//
//                    }
//
//                    Log.e("id", it.id)
//                    Log.e("id", it.content.rendered)
////                        Log.e("response", response.guid.rendered)
//
//                    add(CommentItem("0",it.author_name,it.content.rendered,it.date,0))
//
//
//                }
//                commentAdapter.datas = datas
//                GlobalScope.launch(Dispatchers.Main) {    // 2
//                    commentAdapter.notifyDataSetChanged()
//                    comment_count.text = "댓글 "+check!!.size
//                    var content = comment_count.text.toString()
//                    val spannableString : SpannableString = SpannableString(content)
//                    var start = 2
//
//                    val colorGreenSpan = ForegroundColorSpan(resources.getColor(R.color.green))
//
//                    spannableString.setSpan(colorGreenSpan,start,content.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//
//                    comment_count.text = spannableString
//                }
//
//
//            }
//
//
//        }





        return view
    }

    fun commentSetting(){
        GlobalScope.launch(Dispatchers.Default) {
            Common().dataSetting(context!!.applicationContext,arguments?.getString("id","147").toString(),datas,commentAdapter,comment_count)
        }

    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EventListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                EventDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }


}