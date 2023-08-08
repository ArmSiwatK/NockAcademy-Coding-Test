package com.project.nockAcademyCodingTest.data.model

data class MenuItem(
    val image: String,
    val name: String,
    val price: String,
    var isSelected: Boolean = false
)
