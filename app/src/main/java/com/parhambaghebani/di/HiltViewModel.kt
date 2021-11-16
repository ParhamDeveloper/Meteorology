package com.parhambaghebani.di

import com.parhambaghebani.utility.ApiManager
import com.parhambaghebani.model.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class HiltViewModel {

    @ViewModelScoped
    @Provides
    fun provideLoginRepository(apiManager: ApiManager): MainRepository {
        return MainRepository(apiManager)
    }

}