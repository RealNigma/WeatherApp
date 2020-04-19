package com.realnigma

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {

    private val presenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDI()
        setContentView(R.layout.activity_main)
        initializeForecastList()
    }

    override fun showSpinner() {
        forecastList.visibility = View.GONE
        emptyStateText.visibility = View.GONE
        loadingSpinner.visibility = View.VISIBLE
    }

    override fun hideSpinner() {
        forecastList.visibility = View.VISIBLE
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



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_page_menu, menu)

        val menuItem = menu?.findItem(R.id.search_button)
        val searchMenuItem = menuItem?.actionView

        if (searchMenuItem is SearchView) {
            searchMenuItem.queryHint = getString(R.string.menu_search_hint)
            searchMenuItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    getForecast(query)
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

    private fun getForecast(query: String) = presenter.getForecastForSevenDays(query)

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

    private fun initializeForecastList() {
        forecastList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ForecastAdapter()
        }
    }



}
