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


class CurrentWeatherAdapter() : RecyclerView.Adapter<CurrentWeatherAdapter.ForecastViewHolder>() {

    var currentWeatherList = mutableListOf<ForecastItemViewModel>()

    fun addCurrentWeather(item : ForecastItemViewModel){
        currentWeatherList.clear()
        currentWeatherList.add(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.currentweather_item, parent, false)
        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        currentWeatherList[position].let {
            holder.bind(forecastElement = it)
        }
    }

    override fun getItemCount(): Int {
        return currentWeatherList.size
    }

    class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(forecastElement : ForecastItemViewModel) {
            itemView.degreeText.text = "${forecastElement.temp} °C"
            itemView.descriptionText.text = "${forecastElement.description}"
            itemView.dateText.text = getDate(forecastElement.date)
            Glide.with(itemView.context)
                .load("http://openweathermap.org/img/w/${forecastElement.icon}.png")
                .into(itemView.weatherIcon)
        }

        private fun  getDate(date: Long): String {
            val timeFormatter = SimpleDateFormat("dd.MM.yyyy")
            return timeFormatter.format(Date(date*1000L))
        }

    }

}