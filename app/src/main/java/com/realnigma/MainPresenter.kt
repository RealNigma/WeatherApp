package com.realnigma

import com.realnigma.currentweather.CurrentWeatherDetail
import com.realnigma.currentweather.CurrentWeatherItemViewModel
import com.realnigma.currentweather.CurrentWeatherResponse
import com.realnigma.forecast.ForecastDetail
import com.realnigma.forecast.ForecastItemViewModel
import com.realnigma.forecast.ForecastWeatherResponse
import com.realnigma.forecast.WeatherDescription
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
        api.dailyForecast(cityName).enqueue(object : Callback<ForecastWeatherResponse> {

            override fun onResponse(call: Call<ForecastWeatherResponse>, response: Response<ForecastWeatherResponse>) {
                response.body()?.let {
                    createListForView(it)
                    view.hideSpinner()
                    view.saveCityName()
                } ?: view.showErrorToast(ErrorTypes.NO_RESULT_FOUND)
            }

            override fun onFailure(call: Call<ForecastWeatherResponse>?, t: Throwable) {
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
        api.currentWeather(cityName).enqueue(object : Callback<CurrentWeatherResponse> {

            override fun onResponse(call: Call<CurrentWeatherResponse>, response: Response<CurrentWeatherResponse>) {
                response.body()?.let {
                    currentWeather(it)
                    view.hideSpinner()
                    view.saveCityName()
                } ?: view.showErrorToast(ErrorTypes.NO_RESULT_FOUND)
            }

            override fun onFailure(call: Call<CurrentWeatherResponse>?, t: Throwable) {
                view.showErrorToast(ErrorTypes.CALL_ERROR)
                t.printStackTrace()
            }
        })
    }


    private fun  createListForView(weatherResponse: ForecastWeatherResponse) {
        val forecasts = mutableListOf<ForecastItemViewModel>()
        for (forecastDetail : ForecastDetail in weatherResponse.forecast) {
            val temp = forecastDetail.weatherData.temperature
            val forecastItem = ForecastItemViewModel(
                temp = temp.toInt(),
                date = forecastDetail.date,
                timezone = weatherResponse.city.timezone,
                icon = forecastDetail.description[0].icon,
                description = forecastDetail.description[0].description
            )
            forecasts.add(forecastItem)
        }
        view.updateForecast(forecasts)
    }

    private fun currentWeather(weatherResponse: CurrentWeatherResponse){
       val weatherDetail : CurrentWeatherDetail = weatherResponse.currentWeather
        val weatherDescription : List<WeatherDescription> = weatherResponse.weatherDescription
        val weatherItem =
            CurrentWeatherItemViewModel(
                temp = weatherDetail.temp.toInt(),
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
