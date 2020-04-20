package com.realnigma

interface MainView {
    fun showSpinner()
    fun hideSpinner()
    fun updateForecast(forecasts: List<ForecastItemViewModel>)
    fun showErrorToast(errorType: ErrorTypes)
    fun updateWeather(weather : ForecastItemViewModel)
}
