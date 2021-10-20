package com.example.zmice.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zmice.repository.Repository
import com.example.zmice.models.DefaultSettings
import com.example.zmice.polje.Polje

class GameViewModel:ViewModel() {


    val settings by lazy {
        MutableLiveData<DefaultSettings>(Repository.getSettings())
    }
    fun getSettings():LiveData<DefaultSettings>{
        return settings
    }

    val mapaPolja by lazy {
        MutableLiveData<ArrayList<Polje>>(settings.value?.let { zmica.value?.let { it1 ->
            Repository.getMapaPolja(it,
                it1.x)
        } })
    }
    val zmica by lazy {
        MutableLiveData(settings.value?.let { Repository.getZmica(it) })
    }

    fun setSettings(settings: DefaultSettings = DefaultSettings()){
        this.settings.value = settings
    }



}