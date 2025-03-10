package com.example.md42_rickandmortyapp

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.net.http.NetworkException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @JvmField
    @Rule
    var rule: TestRule = InstantTaskExecutorRule()
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

        @Test
    fun apiService_correctResponse_dataIsEqual(){
        CoroutineScope(Dispatchers.IO).launch {
            val requester = RetrofitHelper.getInstance().create(CharactersAPIGet::class.java)
            val getter = requester.getCharacters(1)

            assertEquals(getter.body()!!.results[0].name, "Rick Sanchez")
        }
    }

    @Test
    fun viewModel_catchedError_errorMessageIsNotNull(){
        val viewModel = MainViewModel()

        viewModel.fetchCharacters(666)

        assertNotNull(viewModel.errorMessage)
    }

}