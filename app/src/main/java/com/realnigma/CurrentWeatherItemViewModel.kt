package com.realnigma

data class CurrentWeatherItemViewModel (val temp : Int,
                                        val icon : String = "010d",
                                        val date : Long = System.currentTimeMillis(),
                                        val description : String = "Без описания",
                                        val city : String = "",
                                        val feelsLike : Int)