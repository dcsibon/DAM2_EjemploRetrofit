package com.dam2_23_24.ejemploretrofit.games.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dam2_23_24.ejemploretrofit.games.ui.viewModel.GamesViewModel

/**
 * Vista de búsqueda de juegos que permite a los usuarios buscar juegos por nombre.
 * Implementa una barra de búsqueda interactiva y muestra los resultados en tiempo real.
 *
 * @param viewModel ViewModel que proporciona el estado de la UI y maneja las operaciones de búsqueda.
 * @param navController Controlador de navegación para manejar la navegación entre pantallas.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchGameView(viewModel: GamesViewModel, navController: NavController) {

    // DCS - Observa el estado actual del texto de búsqueda ingresado por el usuario.
    val query by viewModel.query.collectAsState()
    // DCS - Observa el estado de la barra de búsqueda para saber si está activa.
    val active by viewModel.active.collectAsState()
    // DCS - Observa la lista actual de juegos disponibles para la búsqueda.
    val games by viewModel.games.collectAsState()

    // DCS - Para añadir el componente SearchBar hay que introducir modificar la versión de las siguientes
    // implementaciones o añadirlas si no las tenéis... ver en el build.gradle.kts (Module :app)
    /*
       implementation(platform("androidx.compose:compose-bom:2023.05.01"))
       androidTestImplementation(platform("androidx.compose:compose-bom:2023.05.01"))
    */

    // DCS - Configura y muestra la barra de búsqueda.

    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        query = query,
        onQueryChange = { viewModel.setQuery(it) }, // DCS - Actualiza el texto de búsqueda en el ViewModel.
        onSearch = { viewModel.setActive(false) }, // DCS - Desactiva la búsqueda al presionar el botón de búsqueda.
        active = active,
        onActiveChange = { viewModel.setActive(it) }, // DCS - Actualiza el estado de activación de la búsqueda.
        placeholder = { Text(text = "Search") }, // DCS - Muestra un texto placeholder en la barra de búsqueda.
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "")
        },
        trailingIcon = {
            // DCS - Icono para cerrar la vista de búsqueda o limpiar el texto de búsqueda.
            Icon(imageVector = Icons.Default.Close, contentDescription = "",
                modifier = Modifier.clickable { navController.popBackStack() }
            )
        }
    ) {
        // DCS - Muestra los resultados de la búsqueda si el texto de búsqueda no está vacío.
        if (query.isNotEmpty()) {
            // DCS - Filtra los juegos que coinciden con el texto de búsqueda.
            val filterGames = games.filter { it.name.contains(query, ignoreCase = true) }
            // DCS - Muestra cada juego filtrado como un texto clickeable.
            filterGames.forEach {
                Text(text = it.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(bottom = 10.dp, start = 10.dp)
                        .clickable {
                            // DCS - Navega a los detalles del juego al hacer clic en un resultado de búsqueda.
                            navController.navigate("DetailView/${it.id}")
                        }
                )
            }
        }
    }

}
