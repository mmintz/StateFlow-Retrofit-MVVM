package com.io.moviesflow.repository

import android.media.Rating
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.io.moviesflow.API.MoviesService
import com.io.moviesflow.API.MoviesServiceApi
import com.io.moviesflow.data.Movie

import com.io.moviesflow.data.SearchResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.Request
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception

@RunWith(MockitoJUnitRunner::class)
class MainRepositoryTest {

    private lateinit var repo: MainRepository


    //private val lifecycle = LifecycleRegistry(this)
    private lateinit var dispatcher: CoroutineDispatcher
    lateinit var instantExecutorRule: InstantTaskExecutorRule

    private val scope = TestCoroutineScope()

    private lateinit var result: SearchResult

    @Mock
    private lateinit var moviesService: MoviesService


    //
    // var rule: TestRule = InstantTaskExecutorRule()
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
   // @get:Rule
    //var mockitorule: MockitoRule = MockitoJUnit.rule()
    //@get:Rule
    //val testCoroutineRule = TestCoroutineRule()
    @Before
    fun setup() {
        result = SearchResult("False",
            emptyList(),"0")



        //dispatcher = TestCoroutineDispatcher()
        // Dispatchers.setMain(dispatcher)
        //Dispatchers.resetMain()
    }

    @Test
    fun when_get_movies_returns_succesfull_list_of_movies() = runBlockingTest {

        `when`(moviesService.getMovies("game", "2a4d1b6b", "movie")).thenReturn(SearchResult("success",
            listOf(
            Movie("image","The donkeys","Movie","2000","imddb",false)),"10000"))
        repo = MainRepository(moviesService,scope)
        repo.movies.observeForever() {

        }
       // Thread.sleep(2000)
        Assert.assertTrue(repo.movies.value?.isNotEmpty() == true)
        println("value success "+repo.movies.value)
    }
    @Test
    fun when_get_movies_returns_error() = runBlockingTest {
        val exception = mock(HttpException::class.java)
       // val Instance = mock(MoviesService.apiService::class.java)


        `when`(moviesService.getMovies("game","2a4d1b6b","movie")).thenThrow(exception)
        repo = MainRepository(moviesService,scope)
        repo.movies.observeForever() {

        }
        //println("value error "+repo.movies.value)
        Assert.assertEquals(emptyList<Movie>(),repo.movies.value)

    }



}







