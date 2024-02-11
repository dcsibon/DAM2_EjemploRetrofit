package com.dam2_23_24.ejemploretrofit.games.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dam2_23_24.ejemploretrofit.games.ui.state.SingleGameState
import com.dam2_23_24.ejemploretrofit.games.data.util.Constants.Companion.CUSTOM_BLACK
import com.dam2_23_24.ejemploretrofit.games.ui.viewModel.GamesViewModel

/**
 * Muestra la vista detallada de un juego específico, incluyendo su nombre, imagen, sitio web, calificación y descripción.
 * Utiliza efectos secundarios para gestionar el ciclo de vida de la carga y limpieza de datos.
 *
 * @param viewModel ViewModel asociado que proporciona los datos y operaciones para la vista detallada.
 * @param navController Controlador de navegación para manejar la navegación entre las pantallas de la aplicación.
 * @param id Identificador del juego cuyos detalles se van a mostrar.
 */
@Composable
fun DetailView(viewModel: GamesViewModel, navController: NavController, id: Int) {
    // DCS - Recolecta el StateFlow como estado composable.
    // DCS - Observa cambios en la info del juego del ViewModel. El estado "games" se actualiza automáticamente
    // cuando el ViewModel emite una nueva lista, gracias al uso de "collectAsState".
    val singleGameState by viewModel.singleGameState.collectAsState()

    // DCS - Efecto secundario para cargar los detalles del juego una sola vez al componerse.
    // Se lanza una vez cuando el composable se compone por primera vez. La clave Unit indica que no
    // está vinculado a ninguna variable o estado específico que, al cambiar, debería volver a lanzar el efecto.
    // Por lo tanto, el código dentro del bloque LaunchedEffect se ejecuta una sola vez y no se repite
    // en recomposiciones siguientes a menos que el composable sea completamente reconstruido, es decir,
    // eliminado y vuelto a agregar al árbol de UI.
    // LaunchedEffect garantiza que la carga de datos no se repita innecesariamente con cada recomposición,
    // lo que podría llevar a llamadas redundantes al backend, aumentando el uso de datos y potencialmente
    // degradando el rendimiento de la aplicación.
    LaunchedEffect(Unit) {
        viewModel.fetchGameDetails(id)
    }

    // DCS - Efecto secundario para limpiar el estado en el ViewModel cuando el composable es desechado.
    // Asegurar que cuando el composable asociado es retirado del árbol de UI, ciertos estados en el ViewModel
    // se resetean a valores predeterminados.
    // Esto puede ser útil en escenarios donde deseas asegurarte de que el estado no persista
    // entre diferentes instancias del composable o cuando preparamos el ViewModel para ser
    // reutilizado en un contexto diferente, limpiando cualquier dato anterior.
    DisposableEffect(Unit) {
        onDispose {
            viewModel.clean()
        }
    }

    Scaffold(
        topBar = {
            MainTopBar(title = singleGameState.name,
                showBackButton = true,
                onClickBackButton = { navController.popBackStack() }
            ) {}
        }
    ) {
        ContentDetailView(it, singleGameState)
    }
}

/**
 * Muestra el contenido detallado de un juego, incluyendo su imagen principal, sitio web, calificación y descripción.
 * Este composable implementa un desplazamiento vertical para la descripción del juego, permitiendo al usuario
 * explorar el contenido extenso sin restricciones de espacio de pantalla.
 *
 * La información detallada del juego se presenta a través de una interfaz de usuario coherente y atractiva,
 * fomentando una experiencia de usuario inmersiva al explorar los detalles del juego seleccionado.
 *
 * @param pad Valores de relleno (PaddingValues) aplicados al contenido, generalmente proporcionados por el Scaffold.
 *            Estos valores aseguran que el contenido tenga un espaciado adecuado del resto de los elementos de la UI,
 *            mejorando la legibilidad y la estética general de la pantalla.
 * @param singleGameState Objeto de estado único que contiene los detalles del juego específico a mostrar.
 *                        Este parámetro lleva toda la información relevante del juego seleccionado, como
 *                        la imagen principal, el sitio web, la calificación, y la descripción detallada.
 *                        Al pasar directamente el estado del juego, se facilita la reutilización y la separación de
 *                        la lógica de presentación de los datos, contribuyendo a un diseño de código más limpio y mantenible.
 */
@Composable
fun ContentDetailView(pad: PaddingValues, singleGameState: SingleGameState) {
    Column(
        modifier = Modifier
            .padding(pad)
            .background(Color(CUSTOM_BLACK))
    ) {
        MainImage(imageUrl = singleGameState.background_image)

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 5.dp)
        ) {
            MetaWebsite(singleGameState.website)
            ReviewCard(singleGameState.metacritic)
        }

        // DCS - Se utiliza para crear y recordar un estado de desplazamiento (ScrollState) a través de recomposiciones.
        // El argumento 0 es el valor inicial del desplazamiento. Indica que el contenido comienza sin desplazarse
        // desde su posición original.
        // Cómo el estado de desplazamiento solo afecta a esta presentación visual y es específico de este composable,
        // es adecuado manejarlo directamente dentro del mismo composable.
        val scroll = rememberScrollState(0)

        Text(
            text = singleGameState.description_raw,
            color = Color.White,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, bottom = 10.dp)
                .verticalScroll(scroll)
        )
    }
}

