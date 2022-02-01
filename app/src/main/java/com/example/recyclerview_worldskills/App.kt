package com.example.recyclerview_worldskills

import android.app.Application
import com.example.recyclerview_worldskills.model.UsersService

class App: Application() {

    val usersService = UsersService()
}