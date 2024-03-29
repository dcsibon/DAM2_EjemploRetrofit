package com.dam2_23_24.ejemploretrofit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.dam2_23_24.ejemploretrofit.games.navigation.NavManager
import com.dam2_23_24.ejemploretrofit.ui.theme.EjemploRetrofitTheme
import com.dam2_23_24.ejemploretrofit.games.ui.viewModel.GamesViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Activity principal que sirve como punto de entrada para la UI de la aplicación.
 * Utiliza Jetpack Compose para definir la interfaz de usuario.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel : GamesViewModel by viewModels()

        setContent {
            EjemploRetrofitTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavManager(viewModel)
                }
            }
        }
    }
}
