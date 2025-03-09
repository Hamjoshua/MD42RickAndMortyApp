package com.example.md42_rickandmortyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.md42_rickandmortyapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineExceptionHandler

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
        subscribeToErrorMessage()
    }

    private fun setObserverOnViewModel(){
        viewModel.charsResponce.observe(this, Observer {
            val rView = binding.rView

            it.let{
                rView.layoutManager = LinearLayoutManager(this)
                rView.adapter = CharacterAdapter(it.results)
            }
        })
    }

    private fun subscribeToErrorMessage(){
        viewModel.errorMessage.observe(this, Observer {
            viewModel.errorMessage.value?.let {
                Toast.makeText(this, viewModel.errorMessage.value, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun setButtonsFromPageControl(){
        binding.nextBtn.setOnClickListener{
            val currentPage : Int = viewModel.page.value!!

            viewModel.fetchCharacters(currentPage + 1)
        }

        binding.prevBtn.setOnClickListener{
            val currentPage : Int = viewModel.page.value!!

            viewModel.fetchCharacters(currentPage - 1)
        }
    }

    private fun setButtonListener(){
        binding.fetchBtn.setOnClickListener{
            val etPage = binding.pageEt.text.toString().toInt()
            viewModel.fetchCharacters(etPage)
        }
    }

    private fun initPageEt(){
        viewModel.page.observe(this, Observer {
            binding.pageEt.setText(viewModel.page.value.toString())
        })
    }
}