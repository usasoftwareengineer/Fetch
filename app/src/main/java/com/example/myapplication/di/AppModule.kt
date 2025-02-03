package com.example.myapplication.di

import com.example.myapplication.repository.FetchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    fun provideFetchRepository(): FetchRepository {
        return FetchRepository()
    }
}