package com.example.jetbankapp.domain.repository

import com.example.jetbankapp.common.Resource
import com.example.jetbankapp.data.model.BankData
import kotlinx.coroutines.flow.Flow

interface BankRepository {
    fun getBankDataRepository(): Flow<Resource<BankData>>
}