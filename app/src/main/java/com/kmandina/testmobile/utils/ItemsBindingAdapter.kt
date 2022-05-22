package com.kmandina.testmobile.utils

import android.widget.ImageView
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

