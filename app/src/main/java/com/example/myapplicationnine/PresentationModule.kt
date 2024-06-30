package com.example.myapplicationnine

import android.os.Bundle
import com.example.domain.UseCase
import dagger.Module
import dagger.Provides


@Module
class PresentationModule {

    @Provides
    fun provideListViewModel(useCase: UseCase): ListViewModel = ListViewModel(useCase)

    @Provides
    fun provideDetailViewModel(useCase: UseCase, bundle: Bundle): DetailViewModel = DetailViewModel(useCase, bundle)

}
