package com.realnigma.currentweather

import com.google.gson.annotations.SerializedName
import com.realnigma.currentweather.CurrentWeatherDetail
import com.realnigma.currentweather.SunData
import com.realnigma.currentweather.WindDetail
import com.realnigma.forecast.City
import com.realnigma.forecast.ForecastDetail
import com.realnigma.forecast.WeatherDescription

data class CurrentWeatherResponse (@SerializedName("main") var currentWeather : CurrentWeatherDetail,
                                   @SerializedName("weather") var weatherDescription: List<WeatherDescription>,
                                   @SerializedName("name") var cityName : String,
                                   @SerializedName("dt") var date : Long,
                                   @SerializedName("timezone") var timezone : Long,
                                   @SerializedName("wind") var wind : WindDetail,
                                   @SerializedName("sys") var sunData : SunData
)


