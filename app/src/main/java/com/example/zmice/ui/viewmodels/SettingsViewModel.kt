package com.example.zmice.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zmice.repository.Repository

class SettingsViewModel:ViewModel() {

    val settings by lazy {
        MutableLiveData(Repository.getSettings())
    }


}