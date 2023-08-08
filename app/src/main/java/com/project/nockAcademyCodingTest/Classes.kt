package com.project.nockAcademyCodingTest

data class Restaurant(
    val id: String,
    val image: String,
    val name: String,
    val type: String
)

data class RestaurantDetail(
    val id: String,
    val image: String,
    val name: String,
    val type: String,
    val menu: List<MenuItem>
)

data class MenuItem(
    val image: String,
    val name: String,
    val price: String
)
