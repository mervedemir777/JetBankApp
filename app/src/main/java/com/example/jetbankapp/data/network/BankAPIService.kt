package com.example.jetbankapp.data.network

import com.example.jetbankapp.data.model.BankData

interface BankAPIService {

    @GET("bankdata")
    suspend fun getBankDataNetwork(): BankData

}