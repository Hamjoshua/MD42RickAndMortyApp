package com.example.md42_rickandmortyapp

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersAPIGet {
    // TODO выбор страницы
    @GET("character/?page=1")
    suspend fun getCharacters(@Query("page") page : Int): Response<ParseResult>
}

object RetrofitHelper {
    val baseUrl = "https://rickandmortyapi.com/api/"
    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}