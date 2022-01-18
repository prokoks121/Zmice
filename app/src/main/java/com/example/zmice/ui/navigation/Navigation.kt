package com.example.zmice.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zmice.models.Score
import com.example.zmice.ui.viewmodels.MainViewModel
import com.example.zmice.ui.views.Home
import com.example.zmice.ui.views.SetName
import com.example.zmice.ui.views.SolashScreen
import com.example.zmice.ui.views.Zmice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@SuppressLint("UnrememberedMutableState")
@ExperimentalComposeUiApi
@Composable
fun Navigation(nextNav: State<String>,
               score: State<Score?>,
               viewModel: MainViewModel
) {

    val navigation = rememberNavController()
    val navigationActions = remember(navigation) {
        NavigationActions(navigation)
    }

    NavHost(navController = navigation, startDestination = "SplashScreen") {
        composable("SplashScreen"){
            SolashScreen{
                navigationActions.navigateTo(nextNav.value)
            }
        }
        composable("set_name"){
            SetName{
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.insert(Score(score=0,name = it))
                }
                navigationActions.navigateTo("Home")
            }
        }
        composable("Home") {
            viewModel.getBestScoreScope()
            Home (navigateTo= {
                navigationActions.navigateTo(it,popUp = false)
            },
                score = score,
            )
        }
        composable("Game") {
            Zmice(OnCollisn = {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.insert(Score(score=it,name = "Micaga"))
                }
                navigationActions.navigateTo("Home")
            })
        }
    }

}
