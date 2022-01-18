package com.example.zmice.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zmice.models.DefaultSettings
import com.example.zmice.models.Score
import com.example.zmice.repository.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
public class HomeViewModel @Inject constructor(
    private val repository: GameRepository
): ViewModel(), LifecycleObserver {
    val bestScore by lazy {
        MutableLiveData<Score>()
    }
    fun getBestScoreScope() {
        viewModelScope.launch {
            scoreGet()
        }
    }
    private suspend fun scoreGet(){
        withContext(Dispatchers.IO){
            bestScore.postValue(repository.getScore())

        }
    }

    suspend fun insert(score: Score){
        repository.insert(score)
    }

    fun setSettings(settings:DefaultSettings){
        repository.setSettings(settings = settings)
    }


}