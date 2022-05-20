package com.corporation8793.medicinal_herb.fragment

import android.content.Context
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
import com.corporation8793.medicinal_herb.decoration.EventDecoration
import com.corporation8793.medicinal_herb.R
import com.corporation8793.medicinal_herb.adapter.CommentAdapter
import com.corporation8793.medicinal_herb.dto.CommentItem
import com.corporation8793.medicinal_herb.dto.EventItem
import com.corporation8793.medicinal_herb.herb_wp.rest.RestClient
import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.Comment
import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.Post
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
        val comment_count = view.findViewById<TextView>(R.id.comment_count)

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

        val one_posting : Call<List<Comment>> = RestClient.boardService.retrieveAllComment(arguments?.getString("id","147").toString())

        one_posting.enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                val check : List<Comment>? = response.body()
                var repo =""
                comment_count.text = "댓글 "+check!!.size
                datas.apply {

                    check?.forEach{ it->
                        repo += "$it\n-----------------------"
                        if(true){
                            comment_list["id"] = arrayOf(1,count)
                            add(CommentItem(0,it.author_name,it.content.rendered,it.date,"comment"))
                            count += 1
                        }else{
                            var index = 0;
                            for(i in 0..comment_list["id"]!!.get(1)){
                                index += comment_list["id"]!!.get(0)
                            }

                            comment_list["id"]!![0] = comment_list["id"]!![0] +1
                            add(index,CommentItem(0,it.author_name,it.content.rendered,it.date,"reply"))
                        }


                    }

                    commentAdapter.datas = datas
                    commentAdapter.notifyDataSetChanged()
                }


            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })





        return view
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