package com.example.zmice

import android.app.Application
import com.example.zmice.database.GameDatabase
import com.example.zmice.repository.GameRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@HiltAndroidApp
class GameApplication:Application()