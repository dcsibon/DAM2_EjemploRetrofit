package com.dam2_23_24.ejemploretrofit.views

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dam2_23_24.ejemploretrofit.components.MainImage
import com.dam2_23_24.ejemploretrofit.components.MainTopBar
import com.dam2_23_24.ejemploretrofit.components.MetaWebsite
import com.dam2_23_24.ejemploretrofit.components.ReviewCard
import com.dam2_23_24.ejemploretrofit.util.Constants.Companion.CUSTOM_BLACK
import com.dam2_23_24.ejemploretrofit.viewModel.GamesViewModel

@Composable
fun DetailView(viewModel: GamesViewModel, navController: NavController, id: Int) {

    // DCS -  Este efecto secundario se lanza una vez cuando el composable se compone por primera vez.
    // La clave Unit indica que no está vinculado a ninguna variable o estado específico que, al cambiar,
    // debería volver a lanzar el efecto.
    // Por lo tanto, el código dentro del bloque LaunchedEffect se ejecuta una sola vez y no se repite
    // en recomposiciones subsecuentes a menos que el composable sea completamente reconstruido
    // (es decir, eliminado y vuelto a agregar al árbol de UI).
    // LaunchedEffect garantiza que la carga de datos no se repita innecesariamente con cada recomposición,
    // lo que podría llevar a llamadas redundantes a la base de datos o al backend, aumentando el
    // uso de datos y potencialmente degradando el rendimiento de la aplicación.
    LaunchedEffect(Unit) {
        viewModel.getGameById(id)
    }

    // DCS - Asegurar que cuando el composable asociado es retirado del árbol de UI,
    // ciertos estados en el ViewModel se resetean a valores predeterminados.
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
            MainTopBar(title = viewModel.state.name, showBackButton = true, onClickBackButton = {
                navController.popBackStack()
            }) {}
        }
    ) {
        ContentDetailView(it, viewModel)
    }
}

@Composable
fun ContentDetailView(pad: PaddingValues, viewModel: GamesViewModel) {
    val state = viewModel.state

    Column(
        modifier = Modifier
            .padding(pad)
            .background(Color(CUSTOM_BLACK))
    ) {
        MainImage(imageUrl = state.background_image)

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 5.dp)
        ) {
            MetaWebsite(state.website)
            ReviewCard(state.metacritic)
        }

        val scroll = rememberScrollState(0)

        Text(
            text = state.description_raw,
            color = Color.White,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, bottom = 10.dp)
                .verticalScroll(scroll)
        )
    }
}