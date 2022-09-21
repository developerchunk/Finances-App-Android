package com.developerstring.financesapp.sharedviewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developerstring.financesapp.data.onboarding.OnBoardingStatus
import com.developerstring.financesapp.data.profile.ProfileDataStore
import com.developerstring.financesapp.util.Constants.YES
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {

    // onBoarding
    fun saveOnBoardingStatus(context: Context) {
        viewModelScope.launch {
            OnBoardingStatus(context).saveOnBoardingStatus(YES)
        }
    }

    private val _onBoardingStatus = MutableStateFlow("")
    val onBoardingStatus: StateFlow<String> = _onBoardingStatus
    fun getOnBoardingStatus(context: Context) {
        viewModelScope.launch {
            OnBoardingStatus(context).getOnBoardingStatus.collect {
                _onBoardingStatus.value = it!!
            }
        }
    }

    // Profile Details
    fun saveProfileDetails1(
        context: Context,
        name: String,
        amount: Int,
        currency: String,
    ) {
        val profileDataStore = ProfileDataStore(context)
        viewModelScope.launch {
            profileDataStore.saveName(name)
            profileDataStore.saveTotalAmount(amount)
            profileDataStore.saveCurrency(currency)
        }
    }

    fun saveProfileDetail2(
        context: Context,
        spending: Int,
        savings: Int,
    ) {
        val profileDataStore = ProfileDataStore(context)
        viewModelScope.launch {
            profileDataStore.saveMonthlySpending(spending)
            profileDataStore.saveMonthlySavings(savings)
            profileDataStore.saveProfileCreatedStatus("YES")
        }
    }

    // profile details
    private val _profileName = MutableStateFlow("")
    private val _profileTotalAmount = MutableStateFlow(0)
    private val _profileCurrency = MutableStateFlow("")
    private val _profileSpending = MutableStateFlow(0)
    private val _profileSavings = MutableStateFlow(0)

    val profileName: StateFlow<String> = _profileName
    val profileTotalAmount: StateFlow<Int> = _profileTotalAmount
    val profileCurrency: StateFlow<String> = _profileCurrency
    val profileSpending: StateFlow<Int> = _profileSpending
    val profileSavings: StateFlow<Int> = _profileSavings

    fun getProfileDetails(context: Context) {
        viewModelScope.launch {
            // name
            ProfileDataStore(context = context).getName.collect {
                _profileName.value = it!!
            }
            // total amount
            ProfileDataStore(context = context).getTotalAmount.collect {
                _profileTotalAmount.value = it!!
            }
            // currency
            ProfileDataStore(context = context).getCurrency.collect {
                _profileCurrency.value = it!!
            }
            // spending
            ProfileDataStore(context = context).getMonthlySpending.collect {
                _profileSpending.value = it!!
            }
            // savings
            ProfileDataStore(context = context).getMonthlySavings.collect {
                _profileSavings.value = it!!
            }
        }
    }

    // get the profileCreatedStatus from the data store
    private val _profileCreatedStatus = MutableStateFlow("")
    val profileCreatedStatus: StateFlow<String> = _profileCreatedStatus
    fun getProfileCreatedStatus(context: Context) {
        viewModelScope.launch {
            ProfileDataStore(context = context).getProfileCreatedState.collect {
                _profileCreatedStatus.value = it!!
            }
        }

    }
}