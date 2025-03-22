package com.alekseykostyunin.courses

import android.app.Application
import com.alekseykostyunin.courses.di.AppComponent
import com.alekseykostyunin.courses.di.DaggerAppComponent

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            //.applicationContext(this)
            .build()
    }

}