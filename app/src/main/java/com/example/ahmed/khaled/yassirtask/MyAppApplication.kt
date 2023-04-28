package com.example.ahmed.khaled.yassirtask

import android.ahmed.khaled.core.utils.NetworkingUtils
import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Ahmed Khaled on 28/04/2023.
 */

@HiltAndroidApp
class MyAppApplication : Application() {

    init {
        NetworkingUtils.setNetworkingApplicationContext(this)
    }

}