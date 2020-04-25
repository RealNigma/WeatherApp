package com.realnigma.currentweather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.realnigma.R
import kotlinx.android.synthetic.main.currentweather_item.view.*
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
        return CurrentWeatherViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: CurrentWeatherViewHolder, position: Int) {
        currentWeatherList[position].let {
            holder.bind(currentWeatherElement = it)
        }
    }

    override fun getItemCount(): Int {
        return currentWeatherList.size
    }

    class CurrentWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(currentWeatherElement : CurrentWeatherItemViewModel) {
            var sign: String = defineDegreeSign(currentWeatherElement.temp)
            itemView.degreeText.text = sign + "${currentWeatherElement.temp}°C"

            itemView.descriptionText.text = "${currentWeatherElement.description}"
            itemView.dateText.text = getDate(currentWeatherElement.date)
            itemView.cityName.text = currentWeatherElement.city
            sign = defineDegreeSign(currentWeatherElement.feelsLike)
            itemView.feelsLikeText.text = "Ощущается как: ${sign + currentWeatherElement.feelsLike}°C"
            itemView.humidityText.text = "Влажность: ${currentWeatherElement.humidity}%"
            itemView.windText.text = "Ветер: ${currentWeatherElement.windSpeed} м/с"

            val pressure = ConvertPressure(currentWeatherElement.pressure)
            itemView.pressureText.text = "Давление: ${pressure} мм рт. ст."

            sign = defineDegreeSign(currentWeatherElement.minTemp)
            itemView.minTempText.text = "Мин. температура: ${sign+currentWeatherElement.minTemp}°C"
            sign = defineDegreeSign(currentWeatherElement.maxTemp)
            itemView.maxTempText.text = "Макс. температура: ${sign+currentWeatherElement.maxTemp}°C"

            val timezone : Long = currentWeatherElement.timezone
            itemView.sunriseText.text = "Восход: ${getTime(currentWeatherElement.sunrise+timezone)}"
            itemView.sunsetText.text = "Закат: ${getTime(currentWeatherElement.sunset+timezone)}"


            Glide.with(itemView.context)
                .load("http://openweathermap.org/img/wn/${currentWeatherElement.icon}@2x.png")
                .into(itemView.weatherIcon)
        }

        private fun defineDegreeSign(temp : Int): String {
            val sign: String
            if (temp > 0) {
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

        private fun  getTime(date: Long): String {
            val timeFormatter = SimpleDateFormat("HH:mm")
            return timeFormatter.format(Date(date*1000))
        }
    }

}