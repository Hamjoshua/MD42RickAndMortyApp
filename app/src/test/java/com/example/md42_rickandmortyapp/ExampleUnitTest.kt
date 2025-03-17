package com.example.md42_rickandmortyapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`


val rickSanchez : Character = Character(
    "1",
    "Rick",
    "Sanchez",
    "Human",
    "male",
    "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
)

val fakeResponse : RickAndMortyAPIResponce = RickAndMortyAPIResponce(
    Info(2),
    arrayListOf<Character>(rickSanchez)
)



@ExperimentalCoroutinesApi
class ExampleUnitTest {
    private val sampleDispatcher = Dispatchers.Unconfined
    private lateinit var characterRepository: ICharacterRepository
    private lateinit var viewModel: MainViewModel


    @get:Rule
    val instantTaskExecutorRule : TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(sampleDispatcher)
        characterRepository = mock(ICharacterRepository::class.java)
        viewModel = MainViewModel(characterRepository)
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
    fun viewModel_catchedError_errorMessageIsSite() = runBlocking {
        `when`(characterRepository.getCharacters(1)).thenThrow(RuntimeException("Site"))

        viewModel.fetchCharacters(1)

        val observer = Observer<String> {}
        viewModel.errorMessage.observeForever(observer)
        assertEquals("Site", viewModel.errorMessage.value)
        viewModel.errorMessage.removeObserver(observer)
    }

    @Test
    fun viewModel_catchedError_pageIsNotNormal() = runBlocking {
        viewModel.fetchCharacters(-1)

        val observer = Observer<String> {}
        viewModel.errorMessage.observeForever(observer)
        assertEquals("Доступны страницы от 1 до 999", viewModel.errorMessage.value)
        viewModel.errorMessage.removeObserver(observer)
    }

    @Test
    fun viewModel_gettingData_isSuccess() = runBlocking {
        val result : Result<RickAndMortyAPIResponce> = Result.success(fakeResponse)
        `when`(characterRepository.getCharacters(1)).thenReturn(result)

        viewModel.fetchCharacters(1)

        val observer = Observer<RickAndMortyAPIResponce> {}
        viewModel.charsResponce.observeForever(observer)
        assertEquals(fakeResponse, viewModel.charsResponce.value )
        viewModel.charsResponce.removeObserver(observer)
    }

    @Test
    fun viewModel_gettingData_maxPageIsUpdated() = runBlocking {
        val result : Result<RickAndMortyAPIResponce> = Result.success(fakeResponse)
        `when`(characterRepository.getCharacters(1)).thenReturn(result)

        viewModel.fetchCharacters(1)

        val observer = Observer<Int> {}
        viewModel.maxPage.observeForever(observer)
        assertEquals(fakeResponse.info.pages, viewModel.maxPage.value)
        viewModel.maxPage.removeObserver(observer)
    }

}