package com.example.listycity3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity3.ui.theme.ListyCity3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val cityRepository = CityRepository()
        setContent {
            ListyCity3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CityListScreen(
                        cities = cityRepository.cities, // starter rack of cities
                        onAddCity = {cityRepository.addCity(it)}, // city value will be passed in

                        // A DISCLAIMER
                        // onUpdateCity adjusted per some advice from Google's Gemini (09/18/26)
                        // and some assistance from Kotlin's "higher order functions" page:
                        // (https://kotlinlang.org/docs/lambdas.html#function-types)
                        onUpdateCity = { oldCity, newCity -> cityRepository.updateCity(oldCity, newCity) },
                        // prior functionality overhauled to fix minor bugs that resulted from
                        // me improperly passing oldCity and newCity into the updateCity apparatus
                        // no other text in this file has been altered in any way

                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

/**
 * recommended approach (per lab assignment instructions):
 * 1. track the selected city object
 * 2. store its name and province in text fields
 * 3. create a new city object (updatedCity) with these updated values
 * 4. replace the city in the list
 */