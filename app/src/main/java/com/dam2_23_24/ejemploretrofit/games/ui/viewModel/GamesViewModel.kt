package com.dam2_23_24.ejemploretrofit.games.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dam2_23_24.ejemploretrofit.games.data.GamesRepository
import com.dam2_23_24.ejemploretrofit.games.ui.state.GameInfoState
import com.dam2_23_24.ejemploretrofit.games.ui.state.SingleGameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel que gestiona el estado y la lógica de negocio para la lista de juegos y detalles del juego.
 * Se encarga de comunicarse con el repositorio para obtener datos.
 */
@HiltViewModel
class GamesViewModel @Inject constructor(private val gamesRepository: GamesRepository) : ViewModel() {

    // DCS - Propiedad privada de tipo MutableStateFlow que mantiene la lista actual de juegos (List<GameInfoState>)
    // que la aplicación maneja. Al ser un StateFlow, permite observar cambios en la lista de juegos de
    // manera reactiva. Al ser mutable, esta propiedad puede ser modificada internamente en el ViewModel
    // para reflejar los cambios en los datos
    private val _games = MutableStateFlow<List<GameInfoState>>(emptyList())

    //  DCS - Versión pública inmutable de _games, expuesta como StateFlow<List<GameInfoState>>. Se utiliza para que
    //  la UI puedan observar y reaccionar a los cambios en la lista de juegos de forma segura, sin poder
    //  modificarla directamente. Esto asegura un flujo de datos unidireccional y ayuda a mantener la integridad
    //  de los datos en la arquitectura de la aplicación.
    val games: StateFlow<List<GameInfoState>> = _games.asStateFlow()

    // DCS - De manera similar agregamos la propiedad que representa el estado actual del ViewModel con respecto
    // a un juego específico.
    private val _singleGameState = MutableStateFlow(SingleGameState())
    val singleGameState: StateFlow<SingleGameState> = _singleGameState.asStateFlow()

    // DCS - Propiedad que almacena el valor actual de la consulta de búsqueda. Al ser un MutableStateFlow,
    // permite que la UI reaccione de forma reactiva a sus cambios. La consulta se puede actualizar a medida
    // que el usuario escribe en un campo de búsqueda, permitiendo filtrar los resultados de los juegos de
    // acuerdo con el texto introducido por el usuario.
    val query = MutableStateFlow("")

    // DCS - Esta propiedad se utiliza para mantener el estado de si una búsqueda está activa o no.
    // Al igual que query, como MutableStateFlow, permite una actualización y observación reactiva.
    // Por ejemplo, podría usarse para controlar la visibilidad de una interfaz de usuario de búsqueda o
    // para activar/desactivar funciones específicas de la UI mientras el usuario está en modo de búsqueda.
    val active = MutableStateFlow(false)

    // DCS - Se inicializa con una carga de la lista de juegos.
    init {
        fetchGames()
    }

    /**
     * Carga la lista de juegos desde la API y actualiza el estado de la lista de juegos en el ViewModel.
     */
    private fun fetchGames() {
        viewModelScope.launch {
            _games.value = gamesRepository.getGamesState().results
        }
    }

    /**
     * Carga los detalles de un juego específico por su ID desde la API y actualiza el estado de los detalles del juego en el ViewModel.
     *
     * @param id El ID único del juego cuyos detalles se están solicitando.
     */
    fun fetchGameDetails(id: Int) {
        viewModelScope.launch {
            _singleGameState.value = gamesRepository.getSingleGameState(id)
        }
    }

    /**
     * Reinicia el estado de los detalles del juego a sus valores por defecto.
     */
    fun clean() {
        _singleGameState.value = SingleGameState()
    }

    /**
     * Actualiza la consulta de búsqueda actual.
     *
     * @param newQuery La nueva cadena de texto de consulta para la búsqueda.
     */
    fun setQuery(newQuery: String) {
        query.value = newQuery
    }

    /**
     * Establece si la búsqueda está activa o no.
     *
     * @param newActive El nuevo estado booleano que indica si la búsqueda está activa.
     */
    fun setActive(newActive: Boolean) {
        active.value = newActive
    }

}