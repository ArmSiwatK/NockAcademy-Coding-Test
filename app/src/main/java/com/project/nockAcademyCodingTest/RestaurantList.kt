package com.project.nockAcademyCodingTest

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

@Composable
fun RestaurantList(navController: NavController) {
    var restaurants by remember { mutableStateOf(emptyList<Restaurant>()) }

    val requestQueue = Volley.newRequestQueue(LocalContext.current)
    val url = "https://apiv2-uat.nockacademy.com/restaurants"

    val jsonArrayRequest = JsonArrayRequest(
        Request.Method.GET, url, null,
        { response ->
            val restaurantList = mutableListOf<Restaurant>()
            for (i in 0 until response.length()) {
                val jsonObject = response.getJSONObject(i)
                val restaurant = Restaurant(
                    jsonObject.getString("id"),
                    jsonObject.getString("image"),
                    jsonObject.getString("name"),
                    jsonObject.getString("type")
                )
                restaurantList.add(restaurant)
            }
            restaurants = restaurantList
        },
        { _ ->
            // Handle error
        }
    )

    requestQueue.add(jsonArrayRequest)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Restaurants",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
        )

        LazyColumn {
            items(restaurants) { restaurant ->
                Box(
                    modifier = Modifier.clickable {
                        navController.navigate("restaurantDetail/${restaurant.id}")
                    }
                ) {
                    RestaurantListItem(restaurant)
                }
            }
        }
    }
}