package com.project.nockAcademyCodingTest.data.model

data class RestaurantDetail(
    val id: String,
    val image: String,
    val name: String,
    val type: String,
    val menu: List<MenuItem>
)
