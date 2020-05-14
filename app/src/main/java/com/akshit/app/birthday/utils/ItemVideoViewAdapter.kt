package com.akshit.app.birthday.utils

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RawRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.akshit.app.birthday.R
import com.akshit.app.birthday.inflate
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.item_videos_view.view.*

data class VideoItem(
    @RawRes val redId: Int,
    @RawRes val resIdAudio: Int,
    val pass: String,
    var locked: Boolean = true
)

class ItemVideoViewAdapter(private val items: List<VideoItem>) : RecyclerView.Adapter<ItemVideoViewAdapter.VideoItemHolder>() {

    fun getFinalLocks(): List<Boolean> {
        val locks = mutableListOf<Boolean>()
        items.forEach { locks.add(it.locked) }
        return locks
    }

    internal var player: MediaPlayer? = null

    inner class VideoItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: VideoItem, position: Int) = with(itemView) {
            val uri = "android.resource://" + context.packageName + "/" + item.redId

            video.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("uri", uri)
                it.findNavController().navigate(R.id.action_navigation_dashboard_to_navigation_video, bundle)
            }

//            name_view.text = item.pass

            if (items[position].locked) {
                textInputLayout.visibility = View.VISIBLE
                submit.visibility = View.VISIBLE
                imageView.visibility = View.VISIBLE
                video.visibility = View.GONE
                val params = (audio.layoutParams as ConstraintLayout.LayoutParams)
                params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                audio.layoutParams = params
                reveal_view.visibility = View.VISIBLE
                name_view.setBackgroundColor(Color.TRANSPARENT)
                name_view.setTextColor(Color.WHITE)
                name_view.text = "Can't Guess?"
                imageView.bringToFront()
                input_pass.bringToFront()
                submit.bringToFront()

                reveal_view.setOnClickListener {
                    input_pass.setText(item.pass)
                    submit.callOnClick()
                }

                submit.setOnClickListener {
                    if (input_pass.text.toString().equals(item.pass, ignoreCase = true)) {
                        imageView.setImageResource(R.drawable.ic_lock_open_white_24dp)
                        textInputLayout.visibility = View.GONE
                        submit.visibility = View.GONE
                        reveal_view.visibility = View.GONE

                        items[position].locked = false
                        item.locked = false

                        val handler = Handler()
                        handler.postDelayed({
                            imageView.visibility = View.GONE
                            name_view.setBackgroundColor(Color.WHITE)
                            name_view.setTextColor(ResourcesCompat.getColor(resources, R.color.colorPrimaryDark, null))
                            name_view.text = item.pass
                            name_view.visibility = View.VISIBLE
                            val prm = (audio.layoutParams as ConstraintLayout.LayoutParams)
                            prm.endToEnd = ConstraintLayout.LayoutParams.UNSET
                            audio.layoutParams = prm
                            video.visibility = View.VISIBLE
                        }, 2000)
                    } else {
                        Snackbar.make(itemView, "Wrong guess", Snackbar.LENGTH_SHORT).show()
                    }
                }

                audio.setOnClickListener {
                    if (player?.isPlaying == true) {
                        player?.stop()
                    }
                    player = MediaPlayer.create(context, item.resIdAudio)
                    player?.start()
                }
            } else {
                textInputLayout.visibility = View.GONE
                submit.visibility = View.GONE
                imageView.visibility = View.GONE
                val params = (audio.layoutParams as ConstraintLayout.LayoutParams)
                params.endToEnd = ConstraintLayout.LayoutParams.UNSET
                audio.layoutParams = params
                video.visibility = View.VISIBLE
                reveal_view.visibility = View.GONE
                name_view.setBackgroundColor(Color.WHITE)
                name_view.setTextColor(ResourcesCompat.getColor(resources, R.color.colorPrimaryDark, null))
                name_view.text = item.pass
                name_view.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VideoItemHolder(parent.inflate(R.layout.item_videos_view))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VideoItemHolder, position: Int) = holder.bind(items[position], position)
}