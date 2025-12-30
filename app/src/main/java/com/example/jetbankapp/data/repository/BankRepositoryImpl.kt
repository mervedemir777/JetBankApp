package com.example.jetbankapp.data.repository

import com.example.jetbankapp.common.Resource
import com.example.jetbankapp.data.model.BankData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BankRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : BankRepository {

    override fun getBankDataRepository(): Flow<Resource<BankData>> = flow {
        emit(Resource.Loading())
        val result = runCatching { remoteDataSource.getBankDataSource() }
            .onFailure { emit(Resource.Error(it.message ?: "Error!")) }
            .getOrNull()

        result?.let { emit(Resource.Success(it)) }
    }
}