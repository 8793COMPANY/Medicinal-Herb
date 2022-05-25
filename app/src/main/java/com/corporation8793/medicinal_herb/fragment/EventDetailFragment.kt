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
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.corporation8793.medicinal_herb.Common
import com.corporation8793.medicinal_herb.MySharedPreferences
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
    lateinit var comment_input_box : EditText

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
        val register_btn = view.findViewById<Button>(R.id.register_btn)
        val event_img = view.findViewById<ImageView>(R.id.event_img)
        comment_input_box  = view.findViewById<EditText>(R.id.comment_input_box)
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

        if (arguments?.getString("img","0").equals("0")){
            event_img.setBackgroundResource(R.drawable.banner4)
        }else{
            Glide.with(this).load(arguments?.getString("img","0")).into(event_img)
        }


        divider = EventDecoration(10)


        event_list.addItemDecoration(divider)
        Log.e("id",arguments?.getString("id","147").toString())

        register_btn.setOnClickListener{
            createComment(arguments?.getString("id","147").toString(),"0",comment_input_box.text.toString())
        }

        commentSetting()


        return view
    }

    fun commentSetting(){
        GlobalScope.launch(Dispatchers.Default) {
            Common().dataSetting(context!!.applicationContext,arguments?.getString("id","147").toString(),datas,commentAdapter,comment_count)
        }

    }

    fun createComment(id : String, parent : String, comment : String) {
        GlobalScope.launch(Dispatchers.Default) {
            val testId = MySharedPreferences(context!!.applicationContext).getString("id","hello")
            val testPw = MySharedPreferences(context!!.applicationContext).getString("pw","1234")
            val basicAuth = Credentials.basic(testId, testPw)
            val boardRepository = BoardRepository(basicAuth)


            println("====== UsersRU             ======")
            println("------ isValid             ------")
            try {

                val isValid = boardRepository.createComment(id,parent,comment)

            } catch (e: Exception) {
                Log.e("e", e.toString())
                Log.e("e", e.message.toString())
            }

            GlobalScope.launch(Dispatchers.Main) {
                comment_input_box.setText("")
                commentSetting()
            }


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