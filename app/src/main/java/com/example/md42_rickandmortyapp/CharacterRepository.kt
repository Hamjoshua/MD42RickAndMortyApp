package com.example.md42_rickandmortyapp

interface ICharacterRepository {
    suspend fun getCharacters(page: Int): Result<RickAndMortyAPIResponce>
}

class NetworkCharacterRepository(private val apiService: CharactersAPIGet) : ICharacterRepository {
    override suspend fun getCharacters(page: Int): Result<RickAndMortyAPIResponce> {
        return try {
            val response = apiService.getCharacters(page)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Пустой ответ от сервера"))
            } else {
                Result.failure(Exception("Ошибка сервера: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}