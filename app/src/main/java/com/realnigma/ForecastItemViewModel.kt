package com.realnigma

data class ForecastItemViewModel (val temp : String,
                                  val icon : String = "010d",
                                  val date : Long = System.currentTimeMillis(),
                                  val description : String = "Без описания")