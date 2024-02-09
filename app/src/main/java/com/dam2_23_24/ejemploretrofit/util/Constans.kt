package com.dam2_23_24.ejemploretrofit.util

class Constants {
    companion object{
        const val BASE_URL = "https://api.rawg.io/api/"
        const val ENDPOINT = "games"
        const val API_KEY = "?key=176671a1699f4c3ea0fb1c6297db6c4a"
        const val CUSTOM_BLACK = 0xFF2B2626
        const val CUSTOM_GREEN = 0xFF209B14
    }
}

// DCS - Dirección web con API KEY para traer todos los juegos...
// https://api.rawg.io/api/games?key=176671a1699f4c3ea0fb1c6297db6c4a

// DCS - Dirección web con API KEY para traer infor de un solo juego por su id...
// https://api.rawg.io/api/games/1223?key=176671a1699f4c3ea0fb1c6297db6c4a