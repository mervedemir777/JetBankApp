package com.example.jetbankapp.ui.viewmodel

import com.example.jetbankapp.common.Resource
import com.example.jetbankapp.ui.view.home.HomeScreenState
import kotlinx.coroutines.delay


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: GetBankDataUseCase,
) : ViewModel() {

    private val _homeState = mutableStateOf(HomeScreenState())
    val homeState: State<HomeScreenState> = _homeState

    private val _filteredBankDataList = mutableStateOf<List<BankDataItem>>(emptyList())
    val filteredBankDataList: State<List<BankDataItem>> = _filteredBankDataList

    init {
        getBankList()
    }

    private fun getBankList() = viewModelScope.launch {
        useCase.invoke().collect {
            when (it) {
                is Resource.Error -> {
                    _homeState.value = HomeScreenState(isLoading = false)
                    _homeState.value = HomeScreenState(errorMessage = it.message)
                }

                is Resource.Loading -> {
                    _homeState.value = HomeScreenState(isLoading = true)
                }

                is Resource.Success -> {
                    delay(1250)
                    _homeState.value = HomeScreenState(isLoading = false)
                    _homeState.value = HomeScreenState(bankData = it.data)
                    _filteredBankDataList.value = it.data ?: emptyList()
                }
            }
        }
    }

    fun filterBankDataList(query: String) {
        _filteredBankDataList.value =
            _homeState.value.bankData!!.filter {
                it.bankDistrict!!.contains(query, ignoreCase = true) ||
                        it.bankCity!!.contains(query, ignoreCase = true)
            }
    }


}