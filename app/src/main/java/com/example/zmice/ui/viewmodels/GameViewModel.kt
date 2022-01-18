package com.example.zmice.ui.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.*
import com.example.zmice.repository.GameRepository
import com.example.zmice.models.DefaultSettings
import com.example.zmice.models.Polje
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
public class GameViewModel @Inject constructor(
    private val repository: GameRepository
):ViewModel(), LifecycleObserver {

    val settings by lazy {
        MutableLiveData(repository.getSettings())
    }
    fun getSettings():LiveData<DefaultSettings>{
        return settings
    }


    val mapaPolja by lazy {
        settings.value?.let { zmica.value?.let { it1 ->
            repository.getMapaPolja(it,it1.x).toMutableStateList()
        } }

    }
    val zmica by lazy {
        MutableLiveData(settings.value?.let { repository.getZmica(it) })
    }

    fun setSettings(settings: DefaultSettings = DefaultSettings()){
        this.settings.value = settings
    }

    var score = 0

}

