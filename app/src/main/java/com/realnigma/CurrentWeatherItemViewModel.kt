package com.realnigma

data class CurrentWeatherItemViewModel (val temp : Int,
                                        val icon : String = "010d",
                                        val date : Long = System.currentTimeMillis(),
                                        val timezone : Long,
                                        val description : String = "Без описания",
                                        val city : String = "",
                                        val feelsLike : Int,
                                        val humidity : Int,
                                        val pressure : Int,
                                        val minTemp : Int,
                                        val maxTemp : Int,
                                        val windSpeed : Int,
                                        val sunrise : Long,
                                        val sunset : Long
                                        )