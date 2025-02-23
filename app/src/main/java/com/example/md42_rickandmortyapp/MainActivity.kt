package com.example.md42_rickandmortyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.md42_rickandmortyapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    private val viewModel: MainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtonListener()
        initPageEt()
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