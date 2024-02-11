package com.dam2_23_24.ejemploretrofit.games.ui.views

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.dam2_23_24.ejemploretrofit.games.ui.state.GameInfoState
import com.dam2_23_24.ejemploretrofit.games.data.util.Constants.Companion.CUSTOM_BLACK
import com.dam2_23_24.ejemploretrofit.games.data.util.Constants.Companion.CUSTOM_GREEN

/**
 * Define un componente de barra superior para las vistas principales de la aplicación,
 * con soporte opcional para un botón de retorno.
 *
 * @param title Título a mostrar en la barra superior.
 * @param showBackButton Define si se muestra el botón de retorno.
 * @param onClickBackButton Acción a ejecutar al hacer clic en el botón de retorno.
 * @param onClickAction Acción a ejecutar al hacer clic en el botón de acción.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    title: String,
    showBackButton: Boolean = false,
    onClickBackButton: () -> Unit,
    onClickAction: () -> Unit
) {
    TopAppBar(
        title = { Text(text = title, color = Color.White, fontWeight = FontWeight.ExtraBold) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color(CUSTOM_BLACK)
        ),
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = { onClickBackButton() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
        },
        actions = {
            if (!showBackButton) {
                IconButton(onClick = { onClickAction() }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
        }
    )
}

/**
 * Muestra una tarjeta para un juego, con una acción al hacer clic.
 *
 * @param game Información del juego a mostrar.
 * @param onClick Acción a ejecutar al hacer clic en la tarjeta.
 */
@Composable
fun CardGame(game: GameInfoState, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .padding(10.dp)
            .shadow(40.dp)
            .clickable { onClick() }
    ) {
        Column {
            MainImage(imageUrl = game.background_image)
        }
    }
}

/**
 * Carga y muestra una imagen principal para un juego usando Coil.
 *
 * @param imageUrl URL de la imagen a cargar y mostrar.
 */
@Composable
fun MainImage(imageUrl: String) {
    // DCS - rememberImagePainter es una función de la biblioteca de Coil (Coil Image Loader)
    // utilizada en Jetpack Compose para cargar y mostrar imágenes de manera eficiente.
    // Coil es una biblioteca de carga de imágenes para Android que es compatible con Jetpack Compose.
    //
    // La función rememberImagePainter se encarga de cargar la imagen especificada de manera asíncrona,
    // permitiendo que la UI se mantenga responsiva mientras la imagen se está cargando.

    // Coil maneja internamente un sistema de caché para optimizar la carga de imágenes.
    // Esto significa que si una imagen ya ha sido cargada anteriormente, puede ser recuperada
    // rápidamente de la caché en lugar de ser descargada o cargada de nuevo.
    val image = rememberImagePainter(data = imageUrl)

    Image(
        painter = image,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    )
}

/**
 * Muestra un enlace a un sitio web meta, permitiendo al usuario navegar a dicho sitio.
 *
 * @param url URL del sitio web a abrir.
 */
@Composable
fun MetaWebsite(url: String) {
    // DCS - Preparamos el intent que después se abrirá en el navegador web predeterminado del
    // dispositivo y navegará a la URL especificada...
    val context = LocalContext.current
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

    Column {
        Text(
            text = "METASCORE",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
        )

        Button(
            onClick = { context.startActivity(intent) }, // DCS - Inicia la actividad del navegador.
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Gray
            )
        ) {
            Text(text = "Sitio Web")
        }
    }
}

/**
 * Muestra una tarjeta con la puntuación metacrítica de un juego.
 *
 * @param metascore Puntuación metacrítica del juego.
 */
@Composable
fun ReviewCard(metascore: Int) {
    Card(
        modifier = Modifier
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(CUSTOM_GREEN)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = metascore.toString(),
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 50.sp
            )
        }
    }
}
