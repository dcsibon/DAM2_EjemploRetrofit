package com.dam2_23_24.ejemploretrofit.games.data.util

/**
 * Clase para almacenar constantes globales utilizadas en la aplicación.
 * Incluye URLs de la API, endpoints, claves de API, y colores personalizados para la UI.
 */
class Constants {
    companion object{
        // Base URL de la API de RAWG Video Games Database.
        const val BASE_URL = "https://api.rawg.io/api/"

        // Endpoint específico para acceder a la lista de juegos.
        const val ENDPOINT = "games"

        // Clave de API para autenticar las peticiones a la API de RAWG.
        const val API_KEY = "?key=176671a1699f4c3ea0fb1c6297db6c4a"

        // Color personalizado negro usado en la UI de la aplicación.
        const val CUSTOM_BLACK = 0xFF2B2626

        // Color personalizado verde usado en la UI de la aplicación.
        const val CUSTOM_GREEN = 0xFF209B14
    }
}

// DCS - Ejemplos de cómo se utilizan estas constantes para formar URLs completas para peticiones a la API:

// Dirección web con API KEY para traer todos los juegos...
// https://api.rawg.io/api/games?key=176671a1699f4c3ea0fb1c6297db6c4a

// Dirección web con API KEY para traer información de un solo juego por su id...
// https://api.rawg.io/api/games/1223?key=176671a1699f4c3ea0fb1c6297db6c4a