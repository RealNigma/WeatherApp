package com.realnigma

import com.google.gson.annotations.SerializedName

data class WindDetail (@SerializedName("speed") var speed : Float,
                       @SerializedName("deg") var deg : Float)
