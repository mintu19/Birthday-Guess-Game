package com.akshit.app.birthday.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import com.akshit.app.birthday.R
import kotlinx.android.synthetic.main.fragment_video.*

class VideoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uri = Uri.parse(arguments?.getString("uri"))
        videoView.setVideoURI(uri)

        val controller = MediaController(activity)
        controller.setAnchorView(videoView)

        videoView.start()
    }

    override fun onDestroyView() {
        videoView.stopPlayback()
        super.onDestroyView()
    }
}