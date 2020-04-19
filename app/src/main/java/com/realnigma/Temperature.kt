package com.realnigma

import com.google.gson.annotations.SerializedName

data class Temperature (@SerializedName("temp") var dayTemperature: Double,
                        @SerializedName("night") var nightTemperature: Double,
                        @SerializedName("min") var minTemperature: Double,
                        @SerializedName("max") var maxTemperature: Double)
