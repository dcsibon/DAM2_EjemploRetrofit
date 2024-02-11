package com.dam2_23_24.ejemploretrofit.games.data

import com.dam2_23_24.ejemploretrofit.games.data.model.GameInfoModel
import com.dam2_23_24.ejemploretrofit.games.data.model.GamesModel
import com.dam2_23_24.ejemploretrofit.games.data.model.SingleGameModel
import com.dam2_23_24.ejemploretrofit.games.ui.state.GameInfoState
import com.dam2_23_24.ejemploretrofit.games.ui.state.GamesState
import com.dam2_23_24.ejemploretrofit.games.ui.state.SingleGameState
import javax.inject.Inject

/**
 * Repositorio para gestionar la obtención y transformación de datos de juegos desde una API externa.
 * Proporciona funciones para recuperar información general de juegos y detalles de juegos individuales,
 * transformando los modelos de datos de la API en modelos de estado para la UI.
 *
 * @constructor Crea un `GamesRepository` con una instancia de [ApiGames] para realizar llamadas a la API.
 * @param apiGames Instancia de la interfaz [ApiGames] para acceder a los endpoints de la API de juegos.
 */
class GamesRepository @Inject constructor(private val apiGames: ApiGames) {

    /**
     * Obtiene el estado general de los juegos desde la API y lo transforma en [GamesState].
     * Realiza una llamada a la API para obtener una lista de juegos y sus metadatos asociados.
     *
     * @return Una instancia de [GamesState] que contiene el conteo total de juegos y una lista de [GameInfoState].
     */
    suspend fun getGamesState(): GamesState {
        val response = apiGames.getGames()
        return if (response.isSuccessful) {
            response.body()?.toGamesState() ?: GamesState()
        } else {
            GamesState()
        }
    }

    /**
     * Obtiene el estado de un juego individual por su ID y lo transforma en [SingleGameState].
     * Realiza una llamada a la API para obtener los detalles de un juego específico por su identificador único.
     *
     * @param id El ID único del juego para el cual se están solicitando los detalles.
     * @return Una instancia de [SingleGameState] con los detalles del juego solicitado.
     */
    suspend fun getSingleGameState(id: Int): SingleGameState {
        val response = apiGames.getGameById(id)
        return if (response.isSuccessful) {
            response.body()?.toSingleGameState() ?: SingleGameState()
        } else {
            SingleGameState()
        }
    }

    /**
     * Función de extensión que transforma un [GamesModel] obtenido de la API en un [GamesState] para
     * su uso en la UI.
     * Este método es privado y se utiliza internamente para realizar la transformación de datos.
     *
     * @receiver Un objeto [GamesModel] que representa la respuesta de la API para una lista de juegos.
     * @return Un [GamesState] que representa el estado de la lista de juegos para la UI.
     */
    private fun GamesModel.toGamesState(): GamesState {
        return GamesState(
            count = this.count,
            results = this.results.map { it.toGameInfoState() }
        )
    }

    /**
     * Función de extensión que transforma un [GameInfoModel] en un [GameInfoState].
     * Este método es privado y se utiliza internamente para transformar cada juego en la lista de resultados.
     *
     * @receiver Un objeto [GameInfoModel] que representa los datos básicos de un juego obtenido de la API.
     * @return Un [GameInfoState] con los datos básicos del juego adaptados para la UI.
     */
    private fun GameInfoModel.toGameInfoState(): GameInfoState {
        return GameInfoState(
            id = this.id,
            name = this.name,
            background_image = this.background_image
        )
    }

    /**
     * Transforma un [SingleGameModel] obtenido de la API en un [SingleGameState] para su uso en la UI.
     * Este método es privado y se utiliza internamente para realizar la transformación de los detalles de un juego.
     *
     * @receiver Un objeto [SingleGameModel] que representa los detalles de un juego específico obtenido de la API.
     * @return Un [SingleGameState] que representa el estado detallado del juego para la UI.
     */
    private fun SingleGameModel.toSingleGameState(): SingleGameState {
        return SingleGameState(
            name = this.name,
            description_raw = this.description_raw,
            metacritic = this.metacritic,
            website = this.website,
            background_image = this.background_image
        )
    }
}
