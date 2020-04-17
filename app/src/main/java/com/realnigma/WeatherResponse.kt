package com.realnigma

import com.google.gson.annotations.SerializedName

class WeatherResponse {
    data class City(@SerializedName("name") var cityName : String,
                    @SerializedName("country") var country : String)

}
