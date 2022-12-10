package com.example.food_mood.utils

import android.view.View
import android.widget.ImageView
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.squareup.picasso.Picasso

fun Navigation.doNavigate(v: View, id: NavDirections){
    findNavController(v).navigate(id)
}

fun ImageView.showUrlImage(pictureName : String) {
    var url = "http://kasimadalan.pe.hu/foods/images/$pictureName"
    Picasso.get().load(url).into(this)
}