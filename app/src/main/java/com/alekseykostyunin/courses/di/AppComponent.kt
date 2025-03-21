package com.alekseykostyunin.courses.di

import com.alekseykostyunin.courses.presentation.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}