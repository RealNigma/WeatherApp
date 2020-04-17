package com.realnigma

import javax.inject.Inject

class MainPresenter (val view : MainView){
    @Inject lateinit var api : OpenWeatherAPI
}