package com.example.zmice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.zmice.ui.theme.ZmiceTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.ui.ExperimentalComposeUiApi

import com.example.zmice.ui.navigation.Navigation
import com.example.zmice.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    internal val viewModel: MainViewModel by viewModels()

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val bestScore = viewModel.bestScore.observeAsState()
            viewModel.getBestScoreScope()
            val nextNav = remember {
                mutableStateOf("set_name")
            }
            LaunchedEffect(key1 = bestScore.value) {
                nextNav.value = if (bestScore.value?.name == null) {
                    "set_name"
                } else {
                    "Home"
                }
            }

            ZmiceTheme {
                Navigation(
                    nextNav = nextNav,
                    score = bestScore,
                    viewModel = viewModel
                )
            }

        }
    }
}