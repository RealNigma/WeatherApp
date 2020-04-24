package com.realnigma

import com.google.gson.annotations.SerializedName

data class WeatherResponse (@SerializedName("city") var city : City,
                            @SerializedName("list") var forecast : List<ForecastDetail>,
                            @SerializedName("main") var currentWeather : CurrentWeatherDetail,
                            @SerializedName("weather") var weatherDescription: List<WeatherDescription>,
                            @SerializedName("name") var cityName : String,
                            @SerializedName("dt") var date : Long,
                            @SerializedName("timezone") var timezone : Long,
                            @SerializedName("wind") var wind : WindDetail,
                            @SerializedName("sys") var sunData : SunData
)



