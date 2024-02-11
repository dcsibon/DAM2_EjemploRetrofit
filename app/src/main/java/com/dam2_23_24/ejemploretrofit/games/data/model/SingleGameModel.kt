package com.dam2_23_24.ejemploretrofit.games.data.model

data class SingleGameModel(
    val name : String,
    val description_raw : String,
    val metacritic: Int,
    val website : String,
    val background_image: String
)
