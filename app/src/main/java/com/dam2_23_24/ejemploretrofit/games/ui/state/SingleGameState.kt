package com.dam2_23_24.ejemploretrofit.games.ui.state
/*
data class SingleGameState(
    val name : String = "",
    val description_raw : String = "",
    val metacritic: Int = 0,
    val website : String = "",
    val background_image: String = ""
)
*/

/**
 * Representa el estado de un juego individual en la UI, incluyendo detalles como el nombre, descripción,
 * metacritic, sitio web oficial, y la imagen de fondo.
 */
data class SingleGameState(
    val name: String = "",
    val description_raw: String = "",
    val metacritic: Int = DEFAULT_METACRITIC,
    val website: String = DEFAULT_WEBSITE,
    val background_image: String = ""
) {
    companion object {
        // DCS - Definición de valores por defecto como constantes.

        // DCS - las valoraciones van de 0 a 100, establecemos un valor por defecto fuera del rango (111),
        // de esta manera, después podríamos comprobar si su valor es 111 lo que implicaría que el juego
        // no tiene ninguna valoración.
        const val DEFAULT_METACRITIC = 111
        // DCS - Hacemos algo similar con el valor por defecto de la URL oficial del juego.
        const val DEFAULT_WEBSITE = "sin web"
    }
}