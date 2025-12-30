package com.example.jetbankapp.ui.view.home

import com.example.jetbankapp.data.model.BankDataItem

data class HomeScreenState(
    val isLoading: Boolean = false,
    val bankData: ArrayList<BankDataItem>? = null,
    val errorMessage: String? = null
)