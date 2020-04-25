package com.realnigma

import com.realnigma.currentweather.CurrentWeatherItemViewModel
import com.realnigma.forecast.ForecastItemViewModel

interface MainView {
    fun showSpinner()
    fun hideSpinner()
    fun updateForecast(forecasts: List<ForecastItemViewModel>)
    fun showErrorToast(errorType: ErrorTypes)
    fun updateWeather(weather : CurrentWeatherItemViewModel)
    fun saveCityName()
}
