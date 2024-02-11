package com.dam2_23_24.ejemploretrofit.games.data.di

import com.dam2_23_24.ejemploretrofit.games.data.ApiGames
import com.dam2_23_24.ejemploretrofit.games.data.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Módulo de Hilt que provee dependencias a nivel de aplicación, como el cliente Retrofit.
 * Se utiliza para definir cómo se proporcionan las instancias de ciertos tipos a la aplicación,
 * permitiendo una fácil configuración y centralización de la creación de objetos.
 */
@Module
@InstallIn(SingletonComponent::class) // DCS - Define el ámbito de las dependencias proporcionadas como singleton a nivel de aplicación.
object AppModule {
    // DCS - Define que este módulo es un singleton y estará disponible en el ámbito de la aplicación completa.

    /**
     * Proporciona una instancia única de Retrofit para su uso en toda la aplicación.
     * Retrofit es un cliente HTTP de tipo seguro para Android y Java, facilitando
     * la interacción con APIs web mediante la conversión de la API en una interfaz de Java.
     *
     * @return Retorna una instancia configurada de Retrofit.
     */
    @Singleton // DCS - Indica que la instancia de Retrofit proporcionada es un singleton.
    @Provides // DCS - Anota un método que provee dependencias; Hilt llamará a este método para obtener la instancia requerida.
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL) // DCS - Establece la URL base para las solicitudes HTTP.
            .addConverterFactory(GsonConverterFactory.create()) // DCS - Añade un convertidor para la serialización y deserialización de objetos.
            .build() // DCS - Construye y retorna la instancia de Retrofit.
    }

    /**
     * Proporciona una instancia de la interfaz ApiGames, que es utilizada para acceder a los endpoints de la API de juegos.
     * La instancia es creada por Retrofit utilizando la instancia proporcionada por el método providesRetrofit.
     *
     * @param retrofit La instancia de Retrofit utilizada para crear la interfaz ApiGames.
     * @return Retorna una implementación de la interfaz ApiGames creada por Retrofit.
     */
    @Singleton // DCS - Asegura que la instancia de ApiGames sea única y reutilizable en toda la aplicación.
    @Provides
    fun providesAPiGames(retrofit: Retrofit) : ApiGames {
        return retrofit.create(ApiGames::class.java) // DCS - Crea la implementación de la interfaz ApiGames con Retrofit.
    }

}
