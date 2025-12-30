package com.example.jetbankapp.dependencyinjection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(bankAPIService: BankAPIService): RemoteDataSource {
        return RemoteDataSourceImpl(bankAPIService)
    }
}