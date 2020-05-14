package com.akshit.app.birthday.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.akshit.app.birthday.R
import kotlinx.android.synthetic.main.fragment_secret.*

class SecretFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_secret, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = activity!!.getSharedPreferences("My_Prefs", Context.MODE_PRIVATE)

        web_view.settings.loadWithOverviewMode = true
        web_view.settings.useWideViewPort = true
        web_view.settings.textZoom = 170
        web_view.loadUrl("file:///android_asset/tab4.htm")

        if (preferences.getBoolean("unlocked", false)) {
            lock_view.visibility = View.GONE
            web_view.visibility = View.VISIBLE
        } else {
            pin_view.addTextChangedListener {
                if (it.toString() == "0000") {
                    preferences.edit().apply {
                        putBoolean("unlocked", true)
                        apply()
                    }
                    lock_view.visibility = View.GONE
                    web_view.visibility = View.VISIBLE
                }
            }
        }
    }
}