package com.io.moviesflow.repository

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.io.moviesflow.API.MoviesService
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit


class MainRepositoryTest{

    private lateinit var repo : MainRepository
    //private val lifecycle = LifecycleRegistry(this)


    @Mock
    private lateinit var moviesService: MoviesService



    @Before
    fun setup(){
        repo = MainRepository()
    }

    @Test
    fun when_get_movies_returns_succesfull_list_of_movies() = runBlockingTest {
        repo.movies.observeForever{

        }
        Assert.assertFalse(repo.movies.value?.isEmpty() == false)



    }


}


