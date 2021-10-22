package com.example.zmice

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.zmice.ui.theme.ZmiceTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zmice.models.Score
import com.example.zmice.ui.viewmodels.GameViewModel
import com.example.zmice.ui.viewmodels.MainViewModel
import com.example.zmice.ui.views.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {


    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navigation = rememberNavController()
            val navigationActions = remember(navigation) {
                NavigationActions(navigation)
            }

            val factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    val repository = (application as GameApplication).repository

                    @Suppress("UNCHECKED_CAST")
                    return MainViewModel(
                        repository = repository
                    ) as T
                }
            }
            val viewModel: MainViewModel = viewModel(factory = factory)
            val bestScore = viewModel.bestScore.observeAsState()
            viewModel.getBestScoreScope()
            val nextNav = remember {
                    mutableStateOf("set_name")
            }
            LaunchedEffect(key1 = bestScore.value){
                nextNav.value = if (bestScore.value?.name == null){
                    "set_name"
                }else {
                    "Home"
                }
            }

            ZmiceTheme {
                Navigation(navigation = navigation,
                    navigationActions = navigationActions,
                    nextNav = nextNav,
                    score = bestScore,
                    viewModel=viewModel)
            }
        }
    }


    @SuppressLint("UnrememberedMutableState")
    @ExperimentalComposeUiApi
    @Composable
    fun Navigation(navigation: NavHostController, navigationActions: NavigationActions,nextNav:State<String>,score:  State<Score?>,viewModel: MainViewModel) {

        NavHost(navController = navigation, startDestination = "SplashScreen") {
            composable("SplashScreen"){
                SolashScreen{
                    navigationActions.navigateTo(nextNav.value)
                }
            }
            composable("set_name"){
                SetName{
                    CoroutineScope(Dispatchers.IO).launch {
                        (application as GameApplication).repository.insert(Score(score=0,name = it))
                    }
                    navigationActions.navigateTo("Home")
                }
            }
            composable("Settings"){
                settings()
            }
            composable("Home") {
                viewModel.getBestScoreScope()
                Home (navigateTo= {
                    navigationActions.navigateTo(it,popUp = false)
                },application = application as GameApplication,
                score = score)
            }
            composable("Game") {
                Zmijce(OnCollisn = {
                    CoroutineScope(Dispatchers.IO).launch {
                        (application as GameApplication).repository.insert(Score(score=it,name = "Micaga"))
                    }
                    navigationActions.navigateTo("Home")
                },application= application)
            }
        }

    }
}