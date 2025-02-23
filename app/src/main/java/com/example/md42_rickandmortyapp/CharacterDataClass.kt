package com.example.md42_rickandmortyapp

import android.widget.ImageView
import com.bumptech.glide.Glide

data class Character (
    val id: String,
    val name: String,
    val status: String,
    val type: String,
    val gender: String,
    val image: String
){
    fun setImageTo(view: ImageView){
        Glide.with(view.context).load(image).into(view)
    }
}

data class ParseResult (
    val results: ArrayList<Character>
)