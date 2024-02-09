package com.dam2_23_24.ejemploretrofit.di

import com.dam2_23_24.ejemploretrofit.data.ApiGames
import com.dam2_23_24.ejemploretrofit.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // DCS - Inyección de dependencias de Dagger Hilt.

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesAPiGames(retrofit: Retrofit) : ApiGames {
        return retrofit.create(ApiGames::class.java)
    }

}








