package com.dam2_23_24.ejemploretrofit.games.data

import com.dam2_23_24.ejemploretrofit.games.data.model.GamesModel
import com.dam2_23_24.ejemploretrofit.games.data.model.SingleGameModel
import com.dam2_23_24.ejemploretrofit.games.data.util.Constants.Companion.API_KEY
import com.dam2_23_24.ejemploretrofit.games.data.util.Constants.Companion.ENDPOINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Interfaz para definir los endpoints de la API de juegos utilizada para obtener información sobre juegos.
 * Utiliza Retrofit para mapear las respuestas HTTP a objetos Kotlin.
 */
interface ApiGames {

    /**
     * Obtiene una lista de juegos desde el endpoint especificado.
     * Utiliza una anotación GET para indicar que es una petición HTTP GET.
     * Concatena ENDPOINT con API_KEY para formar la URL completa de la petición.
     *
     * @return Una respuesta de Retrofit que encapsula el modelo de datos [GamesModel] que contiene la lista de juegos.
     *         La función es suspendida para ser llamada dentro de una corutina, permitiendo la ejecución asíncrona.
     */
    @GET(ENDPOINT + API_KEY )
    suspend fun getGames(): Response<GamesModel>

    /**
     * Obtiene los detalles de un juego específico por su ID desde el endpoint.
     * Utiliza una anotación GET, donde el ID del juego se pasa dinámicamente en la URL de la petición.
     * La concatenación de ENDPOINT, el placeholder {id}, y API_KEY forman la URL completa.
     *
     * @param id El ID único del juego cuyos detalles se quieren obtener.
     * @return Una respuesta de Retrofit que encapsula el modelo de datos [SingleGameModel] para el juego específico.
     *         La función es suspendida, lo que indica que debe ser llamada dentro de una corutina para ejecución asíncrona.
     */
    @GET("$ENDPOINT/{id}$API_KEY")
    suspend fun getGameById(@Path(value = "id")id : Int): Response<SingleGameModel>

}