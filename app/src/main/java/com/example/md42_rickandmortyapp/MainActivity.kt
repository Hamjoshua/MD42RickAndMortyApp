package com.example.md42_rickandmortyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }

    private fun setObserverOnViewModel(){
        viewModel.charsResponce.observe(this, Observer {
            val rView = binding.rView

            it.results.let{
                rView.layoutManager = LinearLayoutManager(this)
                rView.adapter = CharacterAdapter(it)
            }
        })
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