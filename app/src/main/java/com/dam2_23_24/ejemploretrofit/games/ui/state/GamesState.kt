package com.dam2_23_24.ejemploretrofit.games.ui.state

data class GamesState(
    val count: Int = 0,
    val results: List<GameInfoState> = emptyList()
)
