package com.example.md42_rickandmortyapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val _page : MutableLiveData<Int> = MutableLiveData<Int>()
    val _charsResponce : MutableLiveData<ParseResult> = MutableLiveData<ParseResult>()

    val page : LiveData<Int> = _page
    val charsResponce : LiveData<ParseResult> get() = _charsResponce

    init{
        _page.value = 1
    }

    fun fetchCharacters(externalPage: Int){
        viewModelScope.launch {
            val requester = RetrofitHelper.getInstance().create(CharactersAPIGet::class.java)
            val getter = requester.getCharacters(externalPage)

            getter.body()?.let {
                val body: ParseResult = it
            }

            _page.value = externalPage
        }
    }
}