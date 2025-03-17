package com.example.md42_rickandmortyapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch



class MainViewModel(private val characterRepository : ICharacterRepository) : ViewModel() {
    companion object {
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CharacterApplication)
                val characterRepository = application.container.characterRepository
                MainViewModel(characterRepository = characterRepository)
            }
        }
    }
    private val _page : MutableLiveData<Int> = MutableLiveData<Int>(1)
    private val _maxPage : MutableLiveData<Int> = MutableLiveData<Int>(999)
    private val _charsResponce : MutableLiveData<RickAndMortyAPIResponce> =
        MutableLiveData<RickAndMortyAPIResponce>()
    private val _errorMessage : MutableLiveData<String> = MutableLiveData<String>()


    val page : LiveData<Int> = _page
    val maxPage : LiveData<Int> get() = _maxPage
    val charsResponce : LiveData<RickAndMortyAPIResponce> get() = _charsResponce
    val errorMessage : LiveData<String> = _errorMessage

    init{
        _page.value = 1
        fetchCharacters(page.value!!)
    }

    fun fetchCharacters(externalPage: Int){
        viewModelScope.launch {
            try{
                if(externalPage < 1 || externalPage > maxPage.value!!){
                    throw Exception("Доступны страницы от 1 до ${maxPage.value!!}")
                }

                val responce = characterRepository.getCharacters(externalPage)
                    .getOrThrow()

                _charsResponce.value = responce
                _page.value = externalPage
                _maxPage.value = responce.info.pages
            }
            catch (error: Exception) {
                _errorMessage.value = error.message
            }

        }
    }
}