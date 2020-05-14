package com.akshit.app.birthday.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.akshit.app.birthday.R
import com.akshit.app.birthday.getRawTextFile
import com.akshit.app.birthday.utils.OnSwipeTouchListener
import kotlinx.android.synthetic.main.fragment_gallery.*


class GalleryFragment : Fragment() {

    private var currentIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val galleryTexts = resources.getRawTextFile(R.raw.images).lines().map { it.split(';') }
        val gallerySize = galleryTexts.size

        if (gallerySize <= 0){
            return
        }

        imageView.setFactory {
            val myView = ImageView(activity)
            myView.scaleType = ImageView.ScaleType.FIT_CENTER
            myView.layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            myView
        }

        imageView.setImageResource(getImageResId(galleryTexts[currentIndex][0]))
        captions.text = galleryTexts[currentIndex][1]

        imageView.setOnTouchListener(object : OnSwipeTouchListener(activity!!) {

            override fun onSwipeRight() {
                super.onSwipeRight()
                currentIndex = if (currentIndex > 0) (currentIndex - 1)%gallerySize else gallerySize-1
                imageView.setImageResource(getImageResId(galleryTexts[currentIndex][0]))
                captions.text = galleryTexts[currentIndex][1]
            }

            override fun onSwipeLeft() {
                super.onSwipeLeft()
                currentIndex = (currentIndex + 1)%gallerySize
                imageView.setImageResource(getImageResId(galleryTexts[currentIndex][0]))
                captions.text = galleryTexts[currentIndex][1]
            }
        })
    }

    fun getImageResId(name: String, type: String = "drawable", pack: String? = activity?.packageName): Int {
        return resources.getIdentifier(name, type, pack)
    }
}