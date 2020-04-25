package com.realnigma

import com.realnigma.currentweather.CurrentWeatherResponse
import com.realnigma.forecast.ForecastWeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherAPI {

    @GET("forecast")
    fun dailyForecast(@Query("q") cityName : String) : Call<ForecastWeatherResponse>

    companion object {
        val BASE_URL = "http://api.openweathermap.org/data/2.5/"
    }

    @GET("weather")
    fun currentWeather(@Query("q") cityName : String) : Call<CurrentWeatherResponse>
}
