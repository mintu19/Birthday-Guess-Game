package com.akshit.app.birthday.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.akshit.app.birthday.R
import com.akshit.app.birthday.getArray
import com.akshit.app.birthday.getRawTextFile
import com.akshit.app.birthday.saveArray
import com.akshit.app.birthday.utils.ItemVideoViewAdapter
import com.akshit.app.birthday.utils.VideoItem
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {

    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val videoTexts = resources.getRawTextFile(R.raw.video).lines().map { it.split(';') }
        val videoSize = videoTexts.size

        val items = mutableListOf<VideoItem>()

        preferences = activity!!.getSharedPreferences("My_Prefs", Context.MODE_PRIVATE)
        val locks = preferences.getArray("locks", true)

        for (i in locks.size until videoSize) {
            locks.add(true)
        }

        videoTexts.forEachIndexed { index, list ->
            val vid = getRawResId(list[0])
            val aid = getRawResId(list[1])
            val name = list[2]
            items.add(VideoItem(vid, aid, name, locks[index]))
        }

        videos_view.apply {
            layoutManager = GridLayoutManager(activity, 1)
            adapter = ItemVideoViewAdapter(items)
            addItemDecoration(DividerItemDecoration(videos_view.context, DividerItemDecoration.HORIZONTAL))
        }
    }

    private fun getRawResId(name: String, type: String = "raw", pack: String? = activity?.packageName): Int {
        return resources.getIdentifier(name, type, pack)
    }

    override fun onDestroyView() {
        val locks = (videos_view.adapter as ItemVideoViewAdapter).getFinalLocks()
        Log.d("destroyer", locks[0].toString())
        preferences.saveArray(locks, "locks")
        super.onDestroyView()
    }
}