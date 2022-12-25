package com.developerstring.financesapp.sharedviewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developerstring.financesapp.data.onboarding.OnBoardingStatus
import com.developerstring.financesapp.data.profile.ProfileDataStore
import com.developerstring.financesapp.roomdatabase.models.ProfileModel
import com.developerstring.financesapp.roomdatabase.repository.ProfileRepository
import com.developerstring.financesapp.util.Constants.DARK_THEME
import com.developerstring.financesapp.util.Constants.PROFILE_ID
import com.developerstring.financesapp.util.Constants.YES
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {

    private var name by mutableStateOf("")
    private var totalAmount by mutableStateOf(0)
    private var currency by mutableStateOf("")
    private var monthlySpent by mutableStateOf(0)
    private var monthlySavings by mutableStateOf(0)
    private var language by mutableStateOf("")

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

    // theme setting
    fun saveThemeSetting(context: Context, darkTheme: Boolean) {
        viewModelScope.launch {
            ProfileDataStore(context).saveThemeSetting(darkTheme)
        }
    }

    private val _themeSetting = MutableStateFlow(true)
    val themeSetting: StateFlow<Boolean> = _themeSetting
    fun getThemeSetting(context: Context) {
        viewModelScope.launch {
            ProfileDataStore(context).getThemeSetting.collect {
                _themeSetting.value = it!!
            }
        }
    }

    // Profile Language
    fun saveProfileLanguage(
        language_: String,
        updateLanguage: Boolean
    ) {
        language = language_
        if (updateLanguage) {
            updateProfileLanguage()
        }
    }

    // Profile Details
    fun saveProfileDetails1(
        name_: String,
        amount_: Int,
        currency_: String,
    ) {

        name = name_
        totalAmount = amount_
        currency = currency_

    }

    fun saveProfileDetail2(
        context: Context,
        spending: Int,
        savings: Int,
    ) {

        monthlySpent = spending
        monthlySavings = savings

        addProfile(
            profileModel = ProfileModel(
                id = PROFILE_ID,
                name = name,
                total_amount = totalAmount,
                currency = currency,
                month_spent = monthlySpent,
                month_saving = monthlySavings,
                theme = DARK_THEME,
                language = language
            )
        )

        val profileDataStore = ProfileDataStore(context)
        viewModelScope.launch {
            profileDataStore.saveProfileCreatedStatus("YES")
        }
    }

    // profile details
    private val _selectedProfile: MutableStateFlow<ProfileModel?> = MutableStateFlow(null)

    private val _profileName = MutableStateFlow("")
    private val _profileTotalAmount = MutableStateFlow(0)
    private val _profileCurrency = MutableStateFlow("")
    private val _profileSpending = MutableStateFlow(0)
    private val _profileSavings = MutableStateFlow(0)
    private val _profileLanguage = MutableStateFlow("")

    val profileName: StateFlow<String> = _profileName
    val profileTotalAmount: StateFlow<Int> = _profileTotalAmount
    val profileCurrency: StateFlow<String> = _profileCurrency
    val profileSpending: StateFlow<Int> = _profileSpending
    val profileSavings: StateFlow<Int> = _profileSavings
    val profileLanguage: StateFlow<String> = _profileLanguage

    fun getProfileDetails() {

        viewModelScope.launch {
            repository.getSelectedProfile(profileId = PROFILE_ID).collect { task ->
                _selectedProfile.value = task
            }
        }

        if (_selectedProfile.value != null) {
            _profileName.value = _selectedProfile.value!!.name
            _profileTotalAmount.value = _selectedProfile.value!!.total_amount
            _profileCurrency.value = _selectedProfile.value!!.currency
            _profileSpending.value = _selectedProfile.value!!.month_spent
            _profileSavings.value = _selectedProfile.value!!.month_saving
            _profileLanguage.value = _selectedProfile.value!!.language
        }
    }

    // save total amount
    fun saveTotalAmount(
        amount: Int
    ) {
        viewModelScope.launch {
            repository.updateProfileAmount(profileId = PROFILE_ID, amount = amount)
        }
    }

    // update Language
    private fun updateProfileLanguage() {
        viewModelScope.launch {
            repository.updateProfileLanguage(profileId = PROFILE_ID, language = language)
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

    private fun addProfile(
        profileModel: ProfileModel
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addProfile(profileModel = profileModel)
        }
    }

    fun updateProfile(
        profileModel: ProfileModel
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateProfile(profileModel = profileModel)
        }
    }
}