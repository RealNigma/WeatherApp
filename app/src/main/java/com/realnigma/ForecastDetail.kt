package com.realnigma

import com.google.gson.annotations.SerializedName

data class ForecastDetail(@SerializedName("dt") var  date: Long,
                          @SerializedName("main") var weatherData : WeatherData,
                          @SerializedName("weather") var description : List<WeatherDescription>,
                          @SerializedName("pressure") var pressure : Double,
                          @SerializedName("humidity") var humidity :Double)
