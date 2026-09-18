package com.example.listycity3

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity3.ui.theme.ListyCity3Theme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.FloatingActionButton
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.ui.graphics.Color

@Composable
fun CityListScreen(
    cities: List<City>,
    onAddCity: (City) -> Unit, // CityListScreen receives function which takes a City object and performs an action
    // nothing is returned
    onUpdateCity: (City, City) -> Unit, // CityListScreen receives function which takes two City objects and performs an action
    // nothing is returned
    modifier: Modifier = Modifier
) {
    var newCityName by remember {mutableStateOf("")} // input city name
    var newProvinceName by remember {mutableStateOf("")} // input province name
    var showAddCityFields by remember {mutableStateOf(false)} // toggles input fields
    // remember: to keep available while CityListScreen on screen
    // mutableStateOf tells Compose to update UI if value changes
    var selectedCity by remember { mutableStateOf<City?>(null) }

    // as derived from lab 3 instruction examples...
    val updatedCity = City(
        name = newCityName,
        province = newProvinceName,
    )
    // to replace a certain city object

    Column(modifier=modifier.fillMaxSize()) {
        Row(
            modifier=Modifier.fillMaxWidth(), // full screen width
            horizontalArrangement = Arrangement.End // button to screen right
        ) {
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    showAddCityFields = !showAddCityFields // toggles between states
                }
            ) {
                Text(
                    text = "+",
                    fontSize = 40.sp // for legibility purposes (it was too small beforehand)
                )
            }
        }
        if(showAddCityFields){
            Row(
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = newCityName,
                    onValueChange = {newCityName=it},
                    label={Text("City")}, // field for city name
                    modifier= Modifier.weight(1f)
                )
                Spacer(modifier=Modifier.width(8.dp))
                OutlinedTextField(
                    value = newProvinceName,
                    onValueChange = {newProvinceName=it},
                    label = {Text("Province")},
                    modifier= Modifier.weight(1f)
                )
                Spacer(modifier= Modifier.width(8.dp))
                Button(
                    modifier = Modifier.padding(vertical = 12.dp),
                    onClick = {
                        if (newCityName.isNotBlank() && newProvinceName.isNotBlank()) {
                            val city = City(
                                name = newCityName,
                                province = newProvinceName
                            )
                            // statements directly below slightly reworked by
                            // Google's Gemini (09/18/26) to fix minor bugs
                            // regarding the calling of nullable functions
                            if (selectedCity == null) {
                                onAddCity(city) // if no selected city (i.e. Add Mode) then use onAddCity function
                            } else {
                                onUpdateCity(selectedCity!!, city) // otherwise use the other one
                                // that now is able to pass in nullables thanks to "!!"
                                // (the extra safeguard renders this useless but it will crash
                                // the code otherwise)
                            }
                            // end of A.I.-assisted snippet
                            // comments in this snippet are explicitly mine (human written)
                            // and based on the lecture notes

                            // once city added/updated now reset everything to base state
                            newCityName = ""
                            newProvinceName = ""
                            showAddCityFields = false
                            selectedCity = null // thus resetting it post-update
                        }
                    }
                ) {
                    if(selectedCity == null) {
                        Text("Add city") // Add City mode
                    }
                    else {
                        Text("Update") // Update City mode
                    }
                }
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(cities) { index, city ->
                CityRow(
                    city = city,
                    // FULL DISCLAIMER
                    // the following segment was reworked by Google Gemini (09/18/26) to link it to the
                    // "clickable" functionality seen in CityRow
                    // comments are mine (human written), only the code was altered
                    isSelected = selectedCity == city, // if selectedCity is city then that is isSelected
                    onClick = {
                        selectedCity = city // selectedCity equals city you tapped on
                        newCityName = city.name
                        newProvinceName = city.province
                        showAddCityFields = true // so you don't have to click the plus button again
                    }
                    // end of A.I.-assisted snippet - no other code was altered outside of these
                )

                if (index < cities.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun CityRow(
    // FULL DISCLAIMER
    // the following snippet was altered by Google's Gemini (09/18/26)
    // to slightly rework the way in which "clickable" was
    // implemented, as seen in the snippet directly above
    city: City,
    isSelected: Boolean,
    onClick: () -> Unit, // links to the onClick seen earlier
    modifier: Modifier = Modifier
    // end of A.I. assisted snippet
    // outside of these snippets no other code has been altered
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() } //
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = city.name,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = city.province,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CityListScreenPreview() {
    ListyCity3Theme {
        CityListScreen(
            cities = listOf(
                City("Edmonton", "AB"),
                City("Vancouver", "BC"),
                City("Calgary", "AB")
            ),
            onAddCity = {},
            onUpdateCity = { _, _ -> } // this particular line of code
            // was mildly altered by Google's Gemini (09/18/26) to
            // link it to the onUpdateCity seen in CityListScreen
            // City (implied) and City (implied) passed into function
            // to yield a Void object (i.e. nothing returned)
            // and some assistance from Kotlin's "higher order functions" page:
            // (https://kotlinlang.org/docs/lambdas.html#function-types)
        )
    }
}