package com.example.md42_rickandmortyapp

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.net.http.NetworkException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class ExampleUnitTest {
    private val sampleDispatcher = Dispatchers.Unconfined
    private lateinit var viewModel: MainViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(sampleDispatcher)
        viewModel = MainViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun apiService_correctResponse_dataIsEqual(){
        CoroutineScope(sampleDispatcher).launch {
            val requester = RetrofitHelper.getInstance().create(CharactersAPIGet::class.java)
            val getter = requester.getCharacters(1)

            assertEquals(getter.body()!!.results[0].name, "Rick Sanchez")
        }
    }

    @Test
    fun viewModel_catchedError_errorMessageIsNotNull(){
        viewModel.fetchCharacters(666)

        assertNotNull(viewModel.errorMessage)
    }

}