package com.corporation8793.medicinal_herb.fragment

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.corporation8793.medicinal_herb.decoration.EventDecoration
import com.corporation8793.medicinal_herb.R
import com.corporation8793.medicinal_herb.adapter.EventAdapter
import com.corporation8793.medicinal_herb.dto.EventItem
import com.corporation8793.medicinal_herb.dto.HerbItem
import com.corporation8793.medicinal_herb.herb_wp.rest.RestClient
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
class EventListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var eventAdapter: EventAdapter
    val datas = mutableListOf<EventItem>()
    lateinit var divider : EventDecoration

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
        val view = inflater.inflate(R.layout.fragment_event_list, container, false)
        val event_list = view.findViewById<RecyclerView>(R.id.event_list)

        val ongoing_event = view.findViewById<Button>(R.id.ongoing_event)
        val end_event = view.findViewById<Button>(R.id.end_event)

        val display : DisplayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(display)
        val height : Int =  (display.heightPixels / 3.5).toInt()

        eventAdapter = EventAdapter(context,height);
        event_list.adapter = eventAdapter

        val lm = LinearLayoutManager(context)
        event_list.layoutManager = lm


        divider = EventDecoration(10)


        event_list.addItemDecoration(divider)

        ongoing_event.setOnClickListener{
            dataSetting(RestClient.CATEGORY_EVENT_ONGOING)
        }

        end_event.setOnClickListener{
            dataSetting(RestClient.CATEGORY_EVENT_DONE)
        }

        dataSetting(RestClient.CATEGORY_EVENT_ONGOING)



//        datas.apply {
//
//            add(EventItem(0, "당첨자 발표: 2022.06.30"))
//            add(EventItem(0, "당첨자 발표: 2022.07.02"))
//            add(EventItem(0, "당첨자 발표: 2022.07.09"))
//
//            eventAdapter.datas = datas
//            eventAdapter.notifyDataSetChanged()
//        }



        return view
    }

    fun dataSetting(category : String){
        val one_posting : Call<List<Post>> = RestClient.boardService.retrievePostInCategories("100","1","desc",category)

        one_posting.enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                val check : List<Post>? = response.body()
                var repo =""

                datas.apply {
                    datas.clear()
                    check?.forEach{ it->

//                        Log.e("length",it.acf.toString())
//                        Log.e("check",it.acf.get(0).announcement_date)
                        if (it.acf.announcement_date !=null) {
                            add(EventItem(it.id, 0, it.acf.announcement_date))

                        }
                        Log.e("it", "$it\n")
                        }
                }

                eventAdapter.datas = datas
                eventAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.e("t",t.message.toString())

            }


        })
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
                EventListFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}