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
            val temp : Int = forecastElement.temp
            val sign : String
            if (temp >= 0) {
                sign = "+"
            }
            else sign = "-"
            itemView.degreeText.text = sign + "${forecastElement.temp}°C"
            itemView.descriptionText.text = "${forecastElement.description}"
            itemView.dateText.text = getDate(forecastElement.date)
            itemView.cityName.text = forecastElement.city
            itemView.feelsLikeText.text = "Ощущается как: ${forecastElement.feelsLike}°C"
            Glide.with(itemView.context)
                .load("http://openweathermap.org/img/wn/${forecastElement.icon}@2x.png")
                .into(itemView.weatherIcon)
        }

        private fun  getDate(date: Long): String {
            val timeFormatter = SimpleDateFormat("dd.MM.yyyy")
            return timeFormatter.format(Date(date*1000L))
        }

    }

}