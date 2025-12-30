package com.example.jetbankapp.domain.usecase

import com.example.jetbankapp.domain.repository.BankRepository

class GetBankDataUseCase @Inject constructor(
    private val bankRepository: BankRepository
) {
    fun invoke() = bankRepository.getBankDataRepository()
}