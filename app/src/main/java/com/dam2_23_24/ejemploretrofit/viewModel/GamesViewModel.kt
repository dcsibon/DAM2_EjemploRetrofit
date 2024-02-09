package com.dam2_23_24.ejemploretrofit.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dam2_23_24.ejemploretrofit.model.GameInfo
import com.dam2_23_24.ejemploretrofit.repository.GamesRepository
import com.dam2_23_24.ejemploretrofit.state.GameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(private val repo: GamesRepository) : ViewModel() {

    companion object {
        // Definición de valores por defecto como constantes
        const val DEFAULT_METACRITIC = 111
        const val DEFAULT_WEBSITE = "sin web"
    }

    private val _games = MutableStateFlow<List<GameInfo>>(emptyList())
    val games: StateFlow<List<GameInfo>> = _games.asStateFlow()

    var state by mutableStateOf(GameState())
        private set

    val query = MutableStateFlow("")
    val active = MutableStateFlow(false)

    init {
        fetchGames()
    }

    private fun fetchGames(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.getGames()
            _games.value = result ?: emptyList()
        }
    }

    fun getGameById(id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.getGameById(id)
            state = state.copy(
                name = result?.name ?: "",
                description_raw = result?.description_raw ?: "",
                //DCS - las valoraciones van de 0 a 100, ponemos un valor por defecto fuera del rango
                //Podríamos después comprobar si es 111 querrá decir que no tiene ninguna valoración.
                metacritic = result?.metacritic ?: DEFAULT_METACRITIC,
                website = result?.website ?: DEFAULT_WEBSITE,
                background_image = result?.background_image ?: ""
            )
        }
    }

    fun clean(){
        state = state.copy(
            name =  "",
            description_raw =  "",
            metacritic =  DEFAULT_METACRITIC,
            website =  DEFAULT_WEBSITE,
            background_image = "",
        )
    }

    // Función para actualizar la query
    fun setQuery(newQuery: String) {
        query.value = newQuery
    }

    // Función para actualizar el estado active
    fun setActive(newActive: Boolean) {
        active.value = newActive
    }

}