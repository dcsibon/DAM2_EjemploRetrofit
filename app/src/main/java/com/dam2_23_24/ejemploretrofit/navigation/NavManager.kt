package com.dam2_23_24.ejemploretrofit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dam2_23_24.ejemploretrofit.viewModel.GamesViewModel
import com.dam2_23_24.ejemploretrofit.views.DetailView
import com.dam2_23_24.ejemploretrofit.views.HomeView
import com.dam2_23_24.ejemploretrofit.views.SearchGameView

@Composable
fun NavManager(viewModel: GamesViewModel){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Home") {
        composable("Home"){
            HomeView(viewModel, navController)
        }
        composable("DetailView/{id}", arguments = listOf(
            navArgument("id") { type = NavType.IntType }
        )  ){
            val id = it.arguments?.getInt("id") ?: 0
            DetailView(viewModel, navController, id)
        }
        composable("SearchGameView"){
            SearchGameView(viewModel, navController)
        }
    }

}