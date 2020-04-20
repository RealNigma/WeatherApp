package com.realnigma

import com.google.gson.annotations.SerializedName

data class WeatherResponse (@SerializedName("city") var city : City,
                            @SerializedName("list") var forecast : List<ForecastDetail>,
                            @SerializedName("main") var currentWeather : CurrentWeatherDetail,
                            @SerializedName("weather") var weatherDescription: List<WeatherDescription>)



