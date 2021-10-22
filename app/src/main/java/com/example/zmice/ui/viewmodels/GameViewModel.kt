package com.example.zmice.ui.viewmodels

import androidx.lifecycle.*
import com.example.zmice.repository.GameRepository
import com.example.zmice.models.DefaultSettings
import com.example.zmice.models.Polje

class GameViewModel(private val repository: GameRepository):ViewModel() {

    val settings by lazy {
        MutableLiveData<DefaultSettings>(repository.getSettings())
    }
    fun getSettings():LiveData<DefaultSettings>{
        return settings
    }

    val mapaPolja by lazy {
        MutableLiveData<ArrayList<Polje>>(settings.value?.let { zmica.value?.let { it1 ->
            repository.getMapaPolja(it,
                it1.x)
        } })
    }
    val zmica by lazy {
        MutableLiveData(settings.value?.let { repository.getZmica(it) })
    }

    fun setSettings(settings: DefaultSettings = DefaultSettings()){
        this.settings.value = settings
    }

    var score = 0

}

