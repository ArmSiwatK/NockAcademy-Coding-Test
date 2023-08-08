package com.project.nockAcademyCodingTest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.project.nockAcademyCodingTest.ui.screens.RestaurantList
import com.project.nockAcademyCodingTest.ui.screens.RestaurantMenu
import com.project.nockAcademyCodingTest.ui.theme.StartingTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StartingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "restaurantList"
                    ) {
                        composable("restaurantList") {
                            RestaurantList(navController = navController)
                        }
                        composable(
                            "restaurantDetail/{restaurantId}",
                            arguments = listOf(navArgument("restaurantId") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            val restaurantId =
                                backStackEntry.arguments?.getString("restaurantId")
                            restaurantId?.let {
                                RestaurantMenu(arguments = it)
                            }
                        }
                    }
                }
            }
        }
    }
}