package com.realnigma

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainPresenter(val view : MainView) {
    @Inject lateinit var api : OpenWeatherAPI

    fun getForecastForSevenDays(cityName : String) {
        view.showSpinner()
        api.dailyForecast(cityName).enqueue(object : Callback<WeatherResponse> {

            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                response.body()?.let {
                    createListForView(it)
                    view.hideSpinner()
                } ?: view.showErrorToast(ErrorTypes.NO_RESULT_FOUND)
            }

            override fun onFailure(call: Call<WeatherResponse>?, t: Throwable) {
                view.showErrorToast(ErrorTypes.CALL_ERROR)
                t.printStackTrace()
            }
        })
    }

    private fun  createListForView(weatherResponse: WeatherResponse) {
        val forecasts = mutableListOf<ForecastItemViewModel>()
        for (forecastDetail : ForecastDetail in weatherResponse.forecast) {
            val dayTemp = forecastDetail.weatherData.temperature
            val forecastItem = ForecastItemViewModel(degreeDay = dayTemp.toString(),
                date = forecastDetail.date,
                icon = forecastDetail.description[0].icon,
                description = forecastDetail.description[0].description)
            forecasts.add(forecastItem)
        }
        view.updateForecast(forecasts)
    }
}
