package com.example.md42_rickandmortyapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel : ViewModel() {
    private val _page : MutableLiveData<Int> = MutableLiveData<Int>()
    private val _charsResponce : MutableLiveData<RickAndMortyAPIResponce> = MutableLiveData<RickAndMortyAPIResponce>()

    val page : LiveData<Int> = _page
    val charsResponce : LiveData<RickAndMortyAPIResponce> get() = _charsResponce

    init{
        _page.value = 1
        fetchCharacters(page.value!!)
    }

    fun fetchCharacters(externalPage: Int){
        viewModelScope.launch {
            val requester = RetrofitHelper.getInstance().create(CharactersAPIGet::class.java)
            val getter = requester.getCharacters(externalPage)

            // Выкидывает ошибку при нестандартном отклике
            if(getter.code() != 200){
                throw Exception(getter.message())
            }

            getter.body()?.let {
                _charsResponce.value = it
            }

            _page.value = externalPage
        }
    }
}