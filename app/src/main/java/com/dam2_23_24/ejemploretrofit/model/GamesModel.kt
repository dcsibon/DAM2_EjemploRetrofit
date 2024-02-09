package com.dam2_23_24.ejemploretrofit.model

data class GamesModel(
    val count: Int,
    val results: List<GameInfo>
)

data class GameInfo(
    val id: Int,
    val name : String,
    val background_image: String
)
