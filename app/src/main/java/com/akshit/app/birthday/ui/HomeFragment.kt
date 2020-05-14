package com.akshit.app.birthday.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.akshit.app.birthday.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            text_msg.justificationMode = JUSTIFICATION_MODE_INTER_WORD
//        }

        web_view.settings.loadWithOverviewMode = true
        web_view.settings.useWideViewPort = true
        web_view.settings.textZoom = 170
        web_view.loadUrl("file:///android_asset/tab1.htm")
    }
}