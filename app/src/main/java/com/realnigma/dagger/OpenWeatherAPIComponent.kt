package com.realnigma.dagger

import com.realnigma.MainPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(OpenWeatherAPIModule::class))
interface OpenWeatherAPIComponent {
    fun inject(presenter: MainPresenter)
}