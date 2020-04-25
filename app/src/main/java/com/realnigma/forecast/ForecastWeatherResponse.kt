package com.realnigma.forecast

import com.google.gson.annotations.SerializedName
import com.realnigma.forecast.City
import com.realnigma.forecast.ForecastDetail
import com.realnigma.forecast.WeatherDescription

data class ForecastWeatherResponse (@SerializedName("city") var city : City,
                                   @SerializedName("list") var forecast : List<ForecastDetail>,
                                   @SerializedName("weather") var weatherDescription: List<WeatherDescription>)


