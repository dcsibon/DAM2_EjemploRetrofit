package com.dam2_23_24.ejemploretrofit.games.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dam2_23_24.ejemploretrofit.games.data.util.Constants.Companion.CUSTOM_BLACK
import com.dam2_23_24.ejemploretrofit.games.ui.viewModel.GamesViewModel

/**
 * Pantalla principal de la aplicación que muestra la barra superior y el contenido principal.
 * Utiliza un [Scaffold] para proporcionar una estructura básica que incluye una barra superior y el contenido.
 *
 * @param viewModel El ViewModel asociado con esta pantalla, utilizado para manejar la lógica de negocio y el estado de la UI.
 * @param navController Controlador de navegación de Jetpack Navigation Component, utilizado para manejar la navegación entre pantallas.
 */
@Composable
fun HomeView(viewModel: GamesViewModel, navController: NavController) {
    // DCS - Scaffold proporciona una estructura básica para la pantalla, incluyendo una barra superior.
    Scaffold(
        topBar = {
            // DCS - MainTopBar es una barra superior personalizada que recibe un título ("API GAMES"),
            // una acción para el botón de regreso (se deja en blanco) y se define una acción en OnClickAction
            // para navegar a otra vista (es la función lambda que sacamos fuera de los paréntesis).
            MainTopBar(title = "API GAMES", onClickBackButton = {}) {
                // DCS - Navega a "SearchGameView" cuando se invoca la acción del botón.
                navController.navigate("SearchGameView")
            }
        }
    ) {
        // DCS - ContentHomeView es el contenido principal de la pantalla, mostrando una lista de juegos.
        // Se pasa el viewModel, el padding (espaciado) proporcionado por Scaffold y el NavController
        // para la navegación.
        ContentHomeView(viewModel, it, navController)
    }
}

/**
 * Muestra el contenido principal de la pantalla Home, que incluye una lista de juegos.
 * Esta función composable se encarga de mostrar los juegos disponibles en una lista desplazable.
 *
 * Utiliza [LazyColumn] para mostrar los juegos de manera eficiente, permitiendo un desplazamiento suave
 * y rendimiento optimizado incluso con una gran cantidad de elementos.
 *
 * @param viewModel El ViewModel que proporciona los datos de los juegos a mostrar.
 * @param pad Los valores de padding aplicados al contenido principal, generalmente proporcionados por [Scaffold].
 * @param navController Controlador de navegación para manejar la navegación entre pantallas.
 */
@Composable
fun ContentHomeView(viewModel: GamesViewModel, pad:PaddingValues, navController: NavController) {
    // DCS - Observa cambios en la lista de juegos del ViewModel. El estado "games" se actualiza automáticamente
    // cuando el ViewModel emite una nueva lista, gracias al uso de "collectAsState".
    val games by viewModel.games.collectAsState()

    // DCS - LazyColumn proporciona una lista vertical desplazable de elementos, optimizada para rendimiento.
    LazyColumn(
        // DCS - Aplica un padding (espaciado) y un color de fondo al contenedor de la lista.
        modifier = Modifier
            .padding(pad)
            .background(Color(CUSTOM_BLACK))
    ){
        // DCS - items construye dinámicamente los elementos de la lista basándose en la lista de juegos observada.
        items(games){ item ->
            // DCS - CardGame muestra un juego individual en forma de tarjeta, con una acción definida para la navegación.
            CardGame(item) {
                // DCS - Navega a "DetailView" pasando el id del juego seleccionado como parte de la ruta.
                navController.navigate("DetailView/${item.id}")
            }
            // DCS - Muestra el nombre del juego con un estilo visual específico.
            Text(text = item.name,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                modifier = Modifier.padding(start = 10.dp)
                )
        }
    }
}