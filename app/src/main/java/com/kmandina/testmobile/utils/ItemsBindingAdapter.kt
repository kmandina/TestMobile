package com.kmandina.testmobile.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.kmandina.testmobile.R
import com.kmandina.testmobile.data.model.Media

//
//@BindingAdapter("imageFromUrl")
//fun bindImageFromUrl(view: ImageView, medias: List<Media>?) {
//    if (!medias.isNullOrEmpty()) {
//
//        for(media in medias){
//
//            if(media.code == "poster"){
//                Glide.with(view.context)
//                    .load(imageUrl)
//                    .transition(DrawableTransitionOptions.withCrossFade())
//                    .circleCrop()
//                    .into(view)
//            }
//
//        }
//
//    }
//    else {
//        Glide.with(view.context)
//        .load(R.drawable.ic_perfil)
//        .transition(DrawableTransitionOptions.withCrossFade())
//        .into(view)
//    }
//}

@BindingAdapter("setDate")
fun bindDateText(textView: TextView, date: String?) {

    if(date != null) {

        val d = date.split("T")
        val t = d[1].split("-")
        var h = t[0].split(":")[0].toInt()
        val m = t[0].split(":")[1]

        var time = ""

        when {
            h>12 -> {
                h -= 12
                time = "$h:$m PM"
            }
            h==12 -> {
                time = "$h:$m PM"
            }
            else -> {
                time = "$h:$m AM"
            }
        }

        textView.text = d[0] + " \n " + time
    }
}

@BindingAdapter("setFloat")
fun bindFloatText(textView: TextView, v: Float) {

    textView.text = "$v"
}

