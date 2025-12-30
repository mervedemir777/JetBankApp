package com.example.jetbankapp.data.repository.datasource

import com.example.jetbankapp.data.model.BankData

interface RemoteDataSource {

    suspend fun getBankDataSource(): BankData

}