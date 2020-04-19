package com.realnigma

import com.google.gson.annotations.SerializedName

data class WeatherData (@SerializedName("temp") var temperature: Double,
                        @SerializedName("feels_like") var feelsLike: Double,
                        @SerializedName("temp_min") var minTemperature: Double,
                        @SerializedName("temp_max") var maxTemperature: Double,
                        @SerializedName("pressure") var pressure : Double,
                        @SerializedName("sea_level") var seaLevel : Double,
                        @SerializedName("grnd_level") var groundLevel : Double,
                        @SerializedName("humidity") var humidity : Double,
                        @SerializedName("temp_kf") var tempKf : Double)
