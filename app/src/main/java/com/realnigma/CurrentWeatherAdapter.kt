package com.realnigma

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.currentweather_item.view.*
import kotlinx.android.synthetic.main.forecast_list_item.view.*
import kotlinx.android.synthetic.main.forecast_list_item.view.dateText
import kotlinx.android.synthetic.main.forecast_list_item.view.degreeText
import kotlinx.android.synthetic.main.forecast_list_item.view.weatherIcon
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.round


class CurrentWeatherAdapter() : RecyclerView.Adapter<CurrentWeatherAdapter.CurrentWeatherViewHolder>() {

    var currentWeatherList = mutableListOf<CurrentWeatherItemViewModel>()

    fun addCurrentWeather(item : CurrentWeatherItemViewModel){
        currentWeatherList.clear()
        currentWeatherList.add(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentWeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.currentweather_item, parent, false)
        return CurrentWeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrentWeatherViewHolder, position: Int) {
        currentWeatherList[position].let {
            holder.bind(forecastElement = it)
        }
    }

    override fun getItemCount(): Int {
        return currentWeatherList.size
    }

    class CurrentWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(forecastElement : CurrentWeatherItemViewModel) {
            var sign: String = defineDegreeSign(forecastElement.temp)
            itemView.degreeText.text = sign + "${forecastElement.temp}°C"

            itemView.descriptionText.text = "${forecastElement.description}"
            itemView.dateText.text = getDate(forecastElement.date)
            itemView.cityName.text = forecastElement.city
            sign = defineDegreeSign(forecastElement.feelsLike)
            itemView.feelsLikeText.text = "Ощущается как: ${sign + forecastElement.feelsLike}°C"
            itemView.humidityText.text = "Влажность: ${forecastElement.humidity}%"

            val pressure = ConvertPressure(forecastElement.pressure)
            itemView.pressureText.text = "Давление: ${pressure} мм рт. ст."

            sign = defineDegreeSign(forecastElement.minTemp)
            itemView.minTempText.text = "Мин. температура: ${sign+forecastElement.minTemp}°C"
            sign = defineDegreeSign(forecastElement.maxTemp)
            itemView.maxTempText.text = "Макс. температура: ${sign+forecastElement.maxTemp}°C"
            Glide.with(itemView.context)
                .load("http://openweathermap.org/img/wn/${forecastElement.icon}@2x.png")
                .into(itemView.weatherIcon)
        }

        private fun defineDegreeSign(temp : Int): String {
            val sign: String
            if (temp >= 0) {
                sign = "+"
            } else sign = ""
            return sign
        }

        private fun ConvertPressure(hpaPressure : Int) : Int {
          var mmghPressure : Double
            mmghPressure = round(hpaPressure * 0.75006375541921)
            return mmghPressure.toInt()
        }

        private fun  getDate(date: Long): String {
            val timeFormatter = SimpleDateFormat("dd.MM.yyyy")
            return timeFormatter.format(Date(date*1000L))
        }

    }

}