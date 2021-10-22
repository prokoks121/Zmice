package com.example.zmice.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zmice.models.Score
import com.example.zmice.repository.GameRepository
import kotlinx.coroutines.*

class MainViewModel(private val repository: GameRepository): ViewModel() {
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


}