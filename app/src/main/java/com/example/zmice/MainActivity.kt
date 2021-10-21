package com.example.zmice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.zmice.ui.theme.ZmiceTheme
import androidx.compose.runtime.*

import androidx.compose.ui.ExperimentalComposeUiApi

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zmice.models.Score
import com.example.zmice.ui.views.Zmijce
import com.example.zmice.ui.views.home
import com.example.zmice.ui.views.settings
import com.example.zmice.ui.views.splashScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
            ZmiceTheme {
                Navigation(navigation = navigation, navigationActions = navigationActions)
            }
        }


    }


    @ExperimentalComposeUiApi
    @Composable
    fun Navigation(navigation: NavHostController, navigationActions: NavigationActions) {
        NavHost(navController = navigation, startDestination = "SplashScreen") {
            composable("SplashScreen"){
                splashScreen{
                    navigationActions.navigateTo("Home")
                }
            }
            composable("Settings"){
                settings()
            }
            composable("Home") {
                home (navigateTo= {
                    navigationActions.navigateTo(it,popUp = false)
                },application = application as GameApplication)
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