package com.example.jetbankapp.dependencyinjection

import com.example.jetbankapp.data.repository.datasource.RemoteDataSource

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideBankRepository(
        remoteDataSource: RemoteDataSource,
    ): BankRepository = BankRepositoryImpl(remoteDataSource)

}