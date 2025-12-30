package com.example.jetbankapp.ui.viewmodel

import androidx.lifecycle.ViewModel


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) : ViewModel() {
    fun logDetailPageEvent(city: String?) {
        firebaseAnalytics.logEvent("event_detail_screen") {
            param("param_detail_city", city ?: "null")
        }
    }
}