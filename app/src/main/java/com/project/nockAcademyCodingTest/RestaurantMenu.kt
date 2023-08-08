package com.project.nockAcademyCodingTest

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberImagePainter

@Composable
fun RestaurantMenu(arguments: String) {
    val requestQueue = Volley.newRequestQueue(LocalContext.current)
    val url = "https://apiv2-uat.nockacademy.com/restaurants/$arguments"

    var restaurantDetail by remember {
        mutableStateOf(
            RestaurantDetail(
                "",
                "",
                "",
                "",
                emptyList()
            )
        )
    }

    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.GET, url, null,
        { response ->
            val id = response.getString("id")
            val image = response.getString("image")
            val name = response.getString("name")
            val type = response.getString("type")

            val menuArray = response.getJSONArray("menu")
            val menuList = mutableListOf<MenuItem>()
            for (i in 0 until menuArray.length()) {
                val menuItemObject = menuArray.getJSONObject(i)
                val menuItem = MenuItem(
                    menuItemObject.getString("image"),
                    menuItemObject.getString("name"),
                    menuItemObject.getString("price")
                )
                menuList.add(menuItem)
            }

            restaurantDetail = RestaurantDetail(id, image, name, type, menuList)
        },
        { _ ->
            // Handle error
        }
    )

    requestQueue.add(jsonObjectRequest)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Menu",
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
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                ) {
                    Text(
                        text = restaurantDetail.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )

                    Text(
                        text = restaurantDetail.type,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    Image(
                        painter = rememberImagePainter(restaurantDetail.image),
                        contentDescription = "Restaurant Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )

                    Text(
                        text = "Menu",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(top = 24.dp, bottom = 24.dp)
                    )
                }
            }
            items(restaurantDetail.menu) { menuItem ->
                RestaurantMenuItem(menuItem)
            }
        }
    }
}