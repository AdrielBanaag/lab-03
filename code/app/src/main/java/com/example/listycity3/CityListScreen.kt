package com.example.listycity3

import android.R.attr.onClick
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
                            onAddCity(
                                City(
                                    name = newCityName,
                                    province = newProvinceName
                                )
                            )
                            newCityName = ""
                            newProvinceName = ""
                            showAddCityFields = false
                        }
                    }
                ) {
                    Text("Add city...")
                }
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(cities) { index, city ->
                CityRow(city = city)

                if (index < cities.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun CityRow(city: City) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .clickable()
        // FILL IN CLICKABLE!
        // reuse text fields to enter updated city name / province
        // use a callback function to send the edit action upward
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
            onAddCity = {}
        )
    }
}