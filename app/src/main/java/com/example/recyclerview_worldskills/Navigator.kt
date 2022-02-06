package com.example.recyclerview_worldskills

import androidx.annotation.StringRes
import com.example.recyclerview_worldskills.model.User

interface Navigator {

    fun showDetails(user: User)

    fun goBack()

    fun toast(@StringRes messageRes: Int)
}