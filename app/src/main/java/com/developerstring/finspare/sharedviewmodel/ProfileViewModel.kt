package com.developerstring.finspare.sharedviewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developerstring.finspare.data.onboarding.OnBoardingStatus
import com.developerstring.finspare.data.profile.ProfileDataStore
import com.developerstring.finspare.roomdatabase.models.CategoryModel
import com.developerstring.finspare.roomdatabase.models.ProfileModel
import com.developerstring.finspare.roomdatabase.repository.CategoryRepository
import com.developerstring.finspare.roomdatabase.repository.ProfileRepository
import com.developerstring.finspare.util.Constants.DARK_THEME
import com.developerstring.finspare.util.Constants.NO
import com.developerstring.finspare.util.Constants.PROFILE_ID
import com.developerstring.finspare.util.Constants.YES
import com.developerstring.finspare.util.TransactionAction
import com.developerstring.finspare.util.calculateContactAmount
import com.developerstring.finspare.util.state.ContactActionState
import com.developerstring.finspare.util.state.ProfileAmountType
import com.developerstring.finspare.util.state.RequestState
import com.developerstring.finspare.util.stringToProfileAmountType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val repositoryCategory: CategoryRepository
) : ViewModel() {

    private var _allProfiles =
        MutableStateFlow<RequestState<List<ProfileModel>>>(RequestState.Idle)
    val allProfiles: StateFlow<RequestState<List<ProfileModel>>> = _allProfiles

    private val _selectedContact: MutableStateFlow<ProfileModel> =
        MutableStateFlow(ProfileModel(currency = "$"))
    val selectedContact: StateFlow<ProfileModel> = _selectedContact

    fun getAllProfiles() {
        _allProfiles.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.getAllProfiles.collect {
                    _allProfiles.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _allProfiles.value = RequestState.Error(e)
        }
    }

    var sortedProfiles: MutableList<ProfileModel> = mutableListOf()

    private var name by mutableStateOf("")
    private var totalAmount by mutableIntStateOf(0)
    private var currency by mutableStateOf("")
    private var monthlySpent by mutableIntStateOf(0)
    private var monthlySavings by mutableIntStateOf(0)
    var language by mutableStateOf("")

    var categoriesSize: MutableState<Int> = mutableIntStateOf(0)

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
    fun saveThemeSetting(theme: String) {
        viewModelScope.launch {
            repository.updateProfileTheme(profileId = PROFILE_ID, theme = theme)
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
        time24Hours: Boolean
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
                language = language,
                time24Hours = time24Hours
            )
        )

        val profileDataStore = ProfileDataStore(context)
        viewModelScope.launch {
            profileDataStore.saveProfileCreatedStatus(YES)
        }
    }

    fun profileCreatedStatus(context: Context) {
        val profileDataStore = ProfileDataStore(context)
        viewModelScope.launch {
            profileDataStore.saveProfileCreatedStatus(NO)
        }
    }

    // profile details
    private val _selectedProfile: MutableStateFlow<ProfileModel> =
        MutableStateFlow(ProfileModel(currency = "$"))
    val selectedProfile: StateFlow<ProfileModel> = _selectedProfile

    private val _profileName = MutableStateFlow("")
    private val _profileTotalAmount = MutableStateFlow(0)
    private val _profileCurrency = MutableStateFlow("$")
    private val _profileLanguage = MutableStateFlow("")
    private val _profileTheme = MutableStateFlow("")
    private val _profileTime24Hours = MutableStateFlow(false)


    val profileName: StateFlow<String> = _profileName
    val profileTotalAmount: StateFlow<Int> = _profileTotalAmount
    val profileCurrency: StateFlow<String> = _profileCurrency
    val profileLanguage: StateFlow<String> = _profileLanguage
    val profileTheme: StateFlow<String> = _profileTheme
    val profileTime24Hours: StateFlow<Boolean> = _profileTime24Hours

    fun getProfileDetails() {

        viewModelScope.launch {
            repository.getSelectedProfile(profileId = PROFILE_ID).collect { task ->
                _selectedProfile.value = task ?: ProfileModel(currency = "$")
            }
        }

        _profileName.value = _selectedProfile.value.name
        _profileTotalAmount.value = _selectedProfile.value.total_amount
        _profileCurrency.value = _selectedProfile.value.currency
        _profileLanguage.value = _selectedProfile.value.language
        _profileTheme.value = _selectedProfile.value.theme
        _profileTime24Hours.value = _selectedProfile.value.time24Hours
    }

    fun contactAction(
        action: ContactActionState,
    ) {
        when (action) {
            ContactActionState.DELETE -> {
                deleteProfile(profileId = contactID.value)
                this.contactActionState.value = ContactActionState.NONE
            }

            else -> {

            }
        }

    }

    fun getProfileAmount(profileId: Int = PROFILE_ID) {
        viewModelScope.launch {
            repository.getProfileAmount(profileId = profileId).collect { amount ->
                _profileTotalAmount.value = amount ?: 0
            }
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

    // get the profileCreatedStatus from the data store
//    private val _messageScanDate = MutableStateFlow("")
    private val _messageScanID = MutableStateFlow("")
//    val messageScanDate: StateFlow<String> = _messageScanDate
    val messageScanIDs: StateFlow<String> = _messageScanID
    fun messageScanDetails(context: Context) {
        viewModelScope.launch {
            val profileDataStore = ProfileDataStore(context = context)
//            profileDataStore.getMessageScanDate.collect {
//                _messageScanDate.value = it ?: ""
//            }
            profileDataStore.getMessageScanIDs.collect {
                _messageScanID.value = it ?: emptyList<String>().toString()
            }
        }
    }

    private val _messageScanEnable = MutableStateFlow(false)
    val messageScanEnable: StateFlow<Boolean> = _messageScanEnable
//    fun getMessageScanEnable(context: Context) {
//        viewModelScope.launch {
//            ProfileDataStore(context = context).getMessageScanEnable.collect {
//                _messageScanEnable.value = it?: false
//            }
//        }
//    }

    fun saveMessageScanEnable(context: Context, enable: Boolean) {
        viewModelScope.launch {
            ProfileDataStore(context = context).saveMessageScanEnable(enable)
        }
    }

//    fun saveMessageScanDate(context: Context, date: String) {
//        viewModelScope.launch {
//            ProfileDataStore(context = context).saveMessageScanDate(date)
//        }
//    }

    fun saveMessageScanID(context: Context, id: String) {
        viewModelScope.launch {
            ProfileDataStore(context = context).saveMessageScanIDs(id)
        }
    }

    fun addProfile(
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

    private fun deleteProfile(profileId: Int) {
        viewModelScope.launch {
            repository.deleteProfile(profileId)
        }
    }

    fun getContactDetails(id: Int) {
        viewModelScope.launch {
            repository.getSelectedProfile(profileId = id).collect { task ->
                _selectedContact.value = task ?: ProfileModel(currency = "$")
            }
        }
    }

    // Category
    private var _allCategory =
        MutableStateFlow<RequestState<List<CategoryModel>>>(RequestState.Idle)
    val allCategories: StateFlow<RequestState<List<CategoryModel>>> =
        _allCategory

    fun getAllCategories() {
        _allCategory.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repositoryCategory.getAllCategories.collect {
                    _allCategory.value =
                        RequestState.Success(it.sortedBy { categoryModel -> categoryModel.category })
                }
            }
        } catch (e: Exception) {
            _allCategory.value = RequestState.Error(e)
        }

    }

    // Selected Category
    var categoryId: MutableState<Int> = mutableIntStateOf(0)
    private var _selectedCategory = MutableStateFlow<CategoryModel?>(null)
    val selectedCategories: StateFlow<CategoryModel?> = _selectedCategory

    fun getSelectedCategories(
        id: Int = categoryId.value
    ) {
        try {
            viewModelScope.launch {
                repositoryCategory.getSelectedCategory(id = id).collect {
                    _selectedCategory.value = it
                }
            }
        } catch (_: Exception) {
        }

    }

    fun addCategory(
        categoryModel: CategoryModel
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryCategory.addCategory(categoryModel = categoryModel)
        }
    }

    fun updateCategoryName(
        id: Int,
        category: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryCategory.updateCategoryName(id = id, category = category)
        }
    }

    fun updateSubCategoryName(
        id: Int,
        subCategory: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryCategory.updateSubCategoryName(id = id, subCategory = subCategory)
        }
    }

    val deleteCategoryState: MutableState<TransactionAction> =
        mutableStateOf(TransactionAction.NO_ACTION)

    var deleteCategoryModel: MutableState<CategoryModel> = mutableStateOf(CategoryModel())

    private fun deleteCategoryExecute() {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryCategory.deleteCategory(categoryModel = deleteCategoryModel.value)
        }
        this.deleteCategoryState.value = TransactionAction.NO_ACTION
    }

    fun categoryAction(
        action: TransactionAction,
    ) {
        when (action) {
            TransactionAction.DELETE -> deleteCategoryExecute()
            else -> {

            }
        }
        this.deleteCategoryState.value = TransactionAction.NO_ACTION
    }

    fun getTime24Hours() {
        viewModelScope.launch {
            repository.getTime24Hours(profileId = PROFILE_ID).collect {
                _profileTime24Hours.value = it ?: false
            }
        }
    }

    fun updateTime24Hours(
        profileId: Int = PROFILE_ID,
        time24Hours: Boolean
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTime24Hours(profileId = profileId, time24Hours = time24Hours)
        }
    }

    fun deleteAllProfiles() {
        viewModelScope.launch {
            repository.deleteAllProfiles()
        }
    }

    // delete all categories
    fun deleteAllCategories() {
        viewModelScope.launch {
            repositoryCategory.deleteAllCategories()
        }
    }

    var contact: MutableState<ProfileModel> = mutableStateOf(ProfileModel())
    var contactID: MutableState<Int> = mutableIntStateOf(1)
    var contactActionState: MutableState<ContactActionState> =
        mutableStateOf(ContactActionState.NONE)

    fun saveContactAmount(
        profileId: Int, amount: Int, amountType: String, context: Context
    ) {
        try {
            viewModelScope.launch {
                repository.updateContactAmount(
                    profileId = profileId,
                    amount = amount,
                    amountType = amountType
                )
            }
        } catch (e: Exception) {
            Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show()
        }
    }

    fun updateContactAmount(
        profileId: Int,
        amount: Int,
        amountType: ProfileAmountType,
        context: Context
    ) {

        val contactValues = calculateContactAmount(
            profileAmount = _selectedContact.value.total_amount,
            amount = amount,
            profileAmountType = _selectedContact.value.amount_type.stringToProfileAmountType(),
            amountType = amountType
        )

        saveContactAmount(
            profileId = profileId,
            amount = contactValues.first,
            amountType = contactValues.second.name,
            context = context
        )

    }

}