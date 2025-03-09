package com.example.md42_rickandmortyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.md42_rickandmortyapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    private val viewModel: MainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setObserverOnViewModel()
        setButtonListener()
        initPageEt()
        setButtonsFromPageControl()
    }

    private fun setObserverOnViewModel(){
        viewModel.charsResponce.observe(this, Observer {
            try {
                val rView = binding.rView

                it.let{
                    rView.layoutManager = LinearLayoutManager(this)
                    rView.adapter = CharacterAdapter(it.results)
                }
            }
            // Вывод ошибки
            catch (error : Exception){
                Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun setButtonsFromPageControl(){
        val currentPage = viewModel.page.value!!
        val maxPage = viewModel.maxPage.value!!

        binding.nextBtn.setOnClickListener{
            if(currentPage < maxPage){
                viewModel.fetchCharacters(currentPage + 1)
            }
        }

        binding.prevBtn.setOnClickListener{
            if(currentPage > 0){
                viewModel.fetchCharacters(currentPage - 1)
            }
        }
    }

    private fun setButtonListener(){
        binding.fetchBtn.setOnClickListener{
            viewModel.fetchCharacters(binding.pageEt.text.toString().toInt())
        }
    }

    private fun initPageEt(){
        binding.pageEt.setText(viewModel.page.value.toString())
    }
}