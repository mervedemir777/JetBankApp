package com.example.jetbankapp.data.repository.datasourceImpl

import com.example.jetbankapp.data.model.BankData
import com.example.jetbankapp.data.network.BankAPIService

class RemoteDataSourceImpl @Inject constructor(
    private val bankAPIService: BankAPIService
): RemoteDataSource {

    override suspend fun getBankDataSource(): BankData {
        return bankAPIService.getBankDataNetwork()
    }
}