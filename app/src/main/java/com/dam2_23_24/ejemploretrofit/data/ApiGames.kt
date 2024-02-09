package com.dam2_23_24.ejemploretrofit.data

import com.dam2_23_24.ejemploretrofit.model.GamesModel
import com.dam2_23_24.ejemploretrofit.model.SingleGameModel
import com.dam2_23_24.ejemploretrofit.util.Constants.Companion.API_KEY
import com.dam2_23_24.ejemploretrofit.util.Constants.Companion.ENDPOINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiGames {

    @GET(ENDPOINT + API_KEY )
    suspend fun getGames(): Response<GamesModel>

    @GET("$ENDPOINT/{id}$API_KEY")
    suspend fun getGameById(@Path(value = "id")id : Int): Response<SingleGameModel>

}