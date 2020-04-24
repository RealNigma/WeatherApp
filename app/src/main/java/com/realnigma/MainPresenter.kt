package com.realnigma

import kotlinx.android.synthetic.main.currentweather_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainPresenter(val view : MainView) {
    @Inject lateinit var api : OpenWeatherAPI

    fun getForecastForFiveDays(cityName : String) {
        if (BuildConfig.OPEN_WEATHER_API_KEY == "YOUR_API_KEY") {
            return
        }
        view.showSpinner()
        api.dailyForecast(cityName).enqueue(object : Callback<WeatherResponse> {

            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                response.body()?.let {
                    createListForView(it)
                    view.hideSpinner()
                    view.saveCityName()
                } ?: view.showErrorToast(ErrorTypes.NO_RESULT_FOUND)
            }

            override fun onFailure(call: Call<WeatherResponse>?, t: Throwable) {
                view.showErrorToast(ErrorTypes.CALL_ERROR)
                t.printStackTrace()
            }
        })
    }

    fun getCurrentWeather(cityName : String) {
        if (BuildConfig.OPEN_WEATHER_API_KEY == "YOUR_API_KEY") {
            view.showErrorToast(ErrorTypes.MISSING_API_KEY)
            return
        }

        view.showSpinner()
        api.currentWeather(cityName).enqueue(object : Callback<WeatherResponse> {

            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                response.body()?.let {
                    currentWeather(it)
                    view.hideSpinner()
                    view.saveCityName()
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
                timezone = weatherResponse.city.timezone,
                icon = forecastDetail.description[0].icon,
                description = forecastDetail.description[0].description)
            forecasts.add(forecastItem)
        }
        view.updateForecast(forecasts)
    }

    private fun currentWeather(weatherResponse: WeatherResponse){
       val weatherDetail : CurrentWeatherDetail = weatherResponse.currentWeather
        val weatherDescription : List<WeatherDescription> = weatherResponse.weatherDescription
        val weatherItem = CurrentWeatherItemViewModel(  temp = weatherDetail.temp.toInt(),
                                                        icon = weatherDescription[0].icon,
                                                        description = weatherDescription[0].description,
                                                        city = weatherResponse.cityName,
                                                        date = weatherResponse.date,
                                                        timezone = weatherResponse.timezone,
                                                        feelsLike = weatherDetail.feelsLike.toInt(),
                                                        humidity = weatherDetail.humidity.toInt(),
                                                        pressure = weatherDetail.pressure.toInt(),
                                                        minTemp = weatherDetail.minTemp.toInt(),
                                                        maxTemp = weatherDetail.maxTemp.toInt(),
                                                        windSpeed = weatherResponse.wind.speed.toInt(),
                                                        sunrise = weatherResponse.sunData.sunrise,
                                                        sunset = weatherResponse.sunData.sunset
                                                )
        view.updateWeather(weatherItem)


    }
}
