package com.realnigma

import kotlinx.android.synthetic.main.currentweather_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainPresenter(val view : MainView) {
    @Inject lateinit var api : OpenWeatherAPI

    fun getForecastForFiveDays(cityName : String) {
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

    fun getCurrentWeather(cityName : String) {
        view.showSpinner()
        api.currentWeather(cityName).enqueue(object : Callback<WeatherResponse> {

            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                response.body()?.let {
                    currentWeather(it)
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
            val temp = forecastDetail.weatherData.temperature
            val forecastItem = ForecastItemViewModel(temp = temp.toInt(),
                date = forecastDetail.date,
                icon = forecastDetail.description[0].icon,
                description = forecastDetail.description[0].description)
            forecasts.add(forecastItem)
        }
        view.updateForecast(forecasts)
    }

    private fun currentWeather(weatherResponse: WeatherResponse){
       val weatherDetail : CurrentWeatherDetail = weatherResponse.currentWeather
        val weatherDescription : List<WeatherDescription> = weatherResponse.weatherDescription
        val weatherItem = CurrentWeatherItemViewModel( temp = weatherDetail.temp.toInt(),
                                                 icon = weatherDescription[0].icon,
                                                 description = weatherDescription[0].description,
                                                 city = weatherResponse.cityName,
                                                 date = weatherResponse.date,
                                                feelsLike = weatherDetail.feelsLike.toInt()
                                                )
        view.updateWeather(weatherItem)


    }
}
