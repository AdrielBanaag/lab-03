package com.example.listycity3
import androidx.compose.runtime.mutableStateListOf

class CityRepository {
    private val _cities = mutableStateListOf( // this list can be changed
        City("Edmonton", "AB"),
        City("Vancouver", "BC"),
        City("Toronto", "ON")
    )

    val cities: List<City> // cities list provided via _cities
        get() = _cities

    fun addCity(city: City) { // means of adding cities
        _cities.add(city)
    }

    // following code is adapted from lab 3 examples
    fun updateCity(prevCity: City, newCity: City) {
        val index = _cities.indexOf(prevCity)
        if (index != -1) {
            _cities[index] = newCity
        }
    }
    // to replace previous city with new city
}