package com.example.md42_rickandmortyapp

import android.app.Application
import retrofit2.Retrofit

interface AppContainer{
    val characterRepository : ICharacterRepository
}

class DefaultContainer : AppContainer{
    private val retrofitHelper : Retrofit = RetrofitHelper.getInstance()
    private val getter = retrofitHelper.create(CharactersAPIGet::class.java)

    override val characterRepository: ICharacterRepository by lazy{
        NetworkCharacterRepository(getter)
    }
}

class CharacterApplication : Application(){
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultContainer()
    }
}