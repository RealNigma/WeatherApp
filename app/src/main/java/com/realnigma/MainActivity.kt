package com.realnigma

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.TabHost
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

 class MainActivity : AppCompatActivity(), MainView {

    private val presenter = MainPresenter(this)
    private var cityName : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //supportActionBar?.setDisplayShowTitleEnabled(false)

        injectDI()
        setContentView(R.layout.activity_main)
        initializeTabHost()
        initializeCurrentWeatherList()
        initializeForecastList()
    }

     private fun initializeTabHost() {
         if (tabHost!= null) {
             tabHost.setup()
             var spec = tabHost.newTabSpec("Текущая погода")
             spec.setContent(R.id.weatherTab)
             spec.setIndicator("Текущая погода")
             tabHost.addTab(spec)

             spec = tabHost.newTabSpec("5 дней")
             spec.setContent(R.id.forecastTab)
             spec.setIndicator("прогноз на 5 дней")
             tabHost.addTab(spec)
         }
     }

     override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val city = cityName
        outState.putString("cityName", city)
         outState.putInt("currentTab", tabHost.currentTab)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val city : String? = savedInstanceState.getString("cityName")
        if  (city != null) {
            cityName = city
            tabHost.currentTab = savedInstanceState.getInt("currentTab")
            getCurrentWeather(city)
            getForecast(city)
        }
    }

    override fun showSpinner() {
        forecastList.visibility = View.GONE
        currentWeatherList.visibility = View.GONE
        emptyStateText.visibility = View.GONE
        loadingSpinner.visibility = View.VISIBLE
    }

    override fun hideSpinner() {
        forecastList.visibility = View.VISIBLE
        currentWeatherList.visibility = View.VISIBLE
        loadingSpinner.visibility = View.GONE
    }

    override fun updateForecast(forecasts: List<ForecastItemViewModel>) {
        if (forecasts.isEmpty()) emptyStateText.visibility = View.VISIBLE
        forecastList.adapter?.safeCast<ForecastAdapter>()?.addForecast(forecasts)
    }

    override fun showErrorToast(errorType: ErrorTypes) {
        when (errorType) {
            ErrorTypes.CALL_ERROR -> toast(getString(R.string.connection_error_message))
            ErrorTypes.NO_RESULT_FOUND -> toast(getString(R.string.city_not_found_toast_message))
        }
        loadingSpinner.visibility = View.GONE
        emptyStateText.visibility = View.VISIBLE
    }

    override fun updateWeather(weather: CurrentWeatherItemViewModel) {
        currentWeatherList.adapter?.safeCast<CurrentWeatherAdapter>()?.addCurrentWeather(weather)
    }
     override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         menuInflater.inflate(R.menu.main_page_menu, menu)

         val menuItem = menu?.findItem(R.id.search_button)
         val searchMenuItem = menuItem?.actionView

         if (searchMenuItem is SearchView) {
             searchMenuItem.queryHint = getString(R.string.menu_search_hint)
             searchMenuItem.setQuery(cityName, false)
            // searchMenuItem.onActionViewExpanded()
             searchMenuItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                 override fun onQueryTextSubmit(query: String): Boolean {
                     getCurrentWeather(query)
                     getForecast(query)
                     cityName = query
                     menuItem.collapseActionView()
                     return false
                 }

                 override fun onQueryTextChange(newText: String?): Boolean {
                     return false
                 }

             })
         }
         return true
     }


    private fun getForecast(query: String) = presenter.getForecastForFiveDays(query)

    private fun getCurrentWeather(query: String) = presenter.getCurrentWeather(query)

    inline fun <reified T> Any.safeCast() = this as? T

    fun Activity.toast(toastMessage: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, toastMessage, duration).show()
    }

    private fun injectDI() {
        DaggerOpenWeatherAPIComponent
            .builder()
            .openWeatherAPIModule(OpenWeatherAPIModule())
            .build()
            .inject(presenter)
    }

     private fun initializeCurrentWeatherList() {
         currentWeatherList.apply {
             layoutManager = LinearLayoutManager(context)
             adapter = CurrentWeatherAdapter()
         }
     }

    private fun initializeForecastList() {
        forecastList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ForecastAdapter()
        }
    }



}
