package com.dam2_23_24.ejemploretrofit.games.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dam2_23_24.ejemploretrofit.games.ui.viewModel.GamesViewModel
import com.dam2_23_24.ejemploretrofit.games.ui.views.DetailView
import com.dam2_23_24.ejemploretrofit.games.ui.views.HomeView
import com.dam2_23_24.ejemploretrofit.games.ui.views.SearchGameView

/**
 * Configura y gestiona la navegación entre diferentes pantallas en la aplicación.
 * Utiliza el componente NavHost de Jetpack Navigation para definir las rutas de navegación
 * y los destinos asociados con cada ruta. Cada destino se asocia con un composable
 * que define la UI de esa pantalla específica.
 *
 * @param viewModel El ViewModel compartido por las pantallas para acceder y manipular los datos.
 */
@Composable
fun NavManager(viewModel: GamesViewModel){
    // DCS - Crea y recuerda una instancia del NavController, que se utiliza para navegar entre composables.
    val navController = rememberNavController()

    // DCS - Define el NavHost, que es el contenedor de navegación que gestiona los composables de destino.
    NavHost(navController = navController, startDestination = "Home") {
        // DCS - Define un destino composable para la pantalla de inicio.
        composable("Home") {
            // DCS - Llama al composable HomeView, pasando el viewModel y navController como parámetros.
            HomeView(viewModel, navController)
        }
        // DCS - Define un destino composable para la pantalla de detalles del juego, incluyendo un argumento dinámico.
        composable("DetailView/{id}", arguments = listOf(
            navArgument("id") { type = NavType.IntType } // DCS - Define el argumento "id" que se espera sea un entero.
        )  ){
            // DCS - Recupera el argumento "id" del destino actual y lo pasa al composable DetailView.
            val id = it.arguments?.getInt("id") ?: 0 // DCS - Obtiene el "id" del juego o 0 si no se encuentra.
            DetailView(viewModel, navController, id)
        }
        // DCS - Define un destino composable para la pantalla de búsqueda de juegos.
        composable("SearchGameView") {
            // DCS - Llama al composable SearchGameView, pasando el viewModel y navController.
            SearchGameView(viewModel, navController)
        }
    }

}