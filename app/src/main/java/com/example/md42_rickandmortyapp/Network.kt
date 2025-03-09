package com.example.md42_rickandmortyapp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersAPIGet {
    // TODO выбор страницы
    @GET("character")
    suspend fun getCharacters(@Query("page") page : Int): Response<RickAndMortyAPIResponce>
}

class RetrofitHelper {
    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"

        fun getInstance(): Retrofit {
                return Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

        }
    }


}