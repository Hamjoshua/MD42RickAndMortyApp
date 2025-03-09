package com.example.md42_rickandmortyapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
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

                val requester = RetrofitHelper.getInstance().create(CharactersAPIGet::class.java)
                val getter = requester.getCharacters(externalPage)

                getter.body()?.let {
                    _charsResponce.value = it
                    _page.value = externalPage
                    _maxPage.value = it.info.pages
                }
            }
            catch (error: Exception) {
                _errorMessage.value = error.message
            }

        }
    }
}