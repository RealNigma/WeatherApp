package com.realnigma.currentweather

import com.google.gson.annotations.SerializedName

data class SunData (@SerializedName("sunrise") var sunrise : Long,
                       @SerializedName("sunset") var sunset : Long)
