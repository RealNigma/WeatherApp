package com.realnigma

import com.google.gson.annotations.SerializedName

data class CurrentWeatherDetail(@SerializedName("dt") var  date: Long,
                                @SerializedName("temp") var temp : Double,
                                @SerializedName("feels_like") var feelsLike : Double,
                                @SerializedName("weather") var description : List<WeatherDescription>,
                                @SerializedName("pressure") var pressure : Double,
                                @SerializedName("humidity") var humidity : Double,
                                @SerializedName("temp_min") var minTemp : Double,
                                @SerializedName("temp_max") var maxTemp : Double,
                                @SerializedName("speed") var wind : Double
                                )
