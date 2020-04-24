package com.realnigma

data class ForecastItemViewModel (val temp : Int,
                                  val icon : String = "010d",
                                  val date : Long = System.currentTimeMillis(),
                                  val timezone : Long,
                                  val description : String = "Без описания")