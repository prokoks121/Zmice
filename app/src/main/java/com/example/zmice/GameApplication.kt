package com.example.zmice

import android.app.Application
import com.example.zmice.database.GameDatabase
import com.example.zmice.repository.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class GameApplication:Application() {
    val database by lazy { GameDatabase.getDatabase(this) }
    val repository by lazy { GameRepository(database.scoreDao()) }
}