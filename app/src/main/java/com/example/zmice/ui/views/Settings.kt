package com.example.zmice.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import com.example.zmice.ui.viewmodels.SettingsViewModel

@Composable
fun settings(viewModel: SettingsViewModel = SettingsViewModel()){

    val settings = viewModel.settings.observeAsState()
    var wall by remember {
        mutableStateOf(settings.value?.wall)
    }
    Box() {
        Column() {
            Row() {

                wall?.let {sWall-> Switch(checked = sWall, onCheckedChange = {
                    wall = it
                settings.value?.wall= it}) }
                }

            }
        }


}