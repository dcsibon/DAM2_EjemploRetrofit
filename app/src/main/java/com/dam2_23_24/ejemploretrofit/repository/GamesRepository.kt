package com.dam2_23_24.ejemploretrofit.repository

import com.dam2_23_24.ejemploretrofit.data.ApiGames
import com.dam2_23_24.ejemploretrofit.model.GameList
import com.dam2_23_24.ejemploretrofit.model.SingleGameModel
import javax.inject.Inject

class GamesRepository @Inject constructor(private val apiGames: ApiGames) {

    suspend fun getGames(): List<GameList>? {
        val response = apiGames.getGames()
        if (response.isSuccessful){
            return response.body()?.results
        }
        return null
    }

    suspend fun getGameById(id: Int): SingleGameModel? {
        val response = apiGames.getGameById(id)
        if(response.isSuccessful){
            return response.body()
        }
        return null
    }

}