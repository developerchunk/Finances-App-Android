package com.developerstring.financesapp.sharedviewmodel

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developerstring.financesapp.data.onboarding.OnBoardingStatus
import com.developerstring.financesapp.data.profile.ProfileDataStore
import com.developerstring.financesapp.roomdatabase.models.CategoryModel
import com.developerstring.financesapp.roomdatabase.models.ProfileModel
import com.developerstring.financesapp.roomdatabase.repository.CategoryRepository
import com.developerstring.financesapp.roomdatabase.repository.ProfileRepository
import com.developerstring.financesapp.util.Constants.DARK_THEME
import com.developerstring.financesapp.util.Constants.PROFILE_ID
import com.developerstring.financesapp.util.Constants.YES
import com.developerstring.financesapp.util.TransactionAction
import com.developerstring.financesapp.util.state.RequestState
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

    private var name by mutableStateOf("")
    private var totalAmount by mutableStateOf(0)
    private var currency by mutableStateOf("")
    private var monthlySpent by mutableStateOf(0)
    private var monthlySavings by mutableStateOf(0)
    var language by mutableStateOf("")

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
    private val _selectedProfile: MutableStateFlow<ProfileModel> =
        MutableStateFlow(ProfileModel(currency = "$"))
    val selectedProfile: StateFlow<ProfileModel> = _selectedProfile

    private val _profileName = MutableStateFlow("")
    private val _profileTotalAmount = MutableStateFlow(0)
    private val _profileCurrency = MutableStateFlow("$")
    private val _profileLanguage = MutableStateFlow("")
    private val _profileTheme = MutableStateFlow("")

    val profileName: StateFlow<String> = _profileName
    val profileTotalAmount: StateFlow<Int> = _profileTotalAmount
    val profileCurrency: StateFlow<String> = _profileCurrency
    val profileLanguage: StateFlow<String> = _profileLanguage
    val profileTheme: StateFlow<String> = _profileTheme

    fun getProfileDetails() {

        viewModelScope.launch {
            repository.getSelectedProfile(profileId = PROFILE_ID).collect { task ->
                _selectedProfile.value = task ?: ProfileModel(currency = "$")
            }
        }

        if (_selectedProfile.value != null) {
            _profileName.value = _selectedProfile.value!!.name
            _profileTotalAmount.value = _selectedProfile.value!!.total_amount
            _profileCurrency.value = _selectedProfile.value!!.currency
            _profileLanguage.value = _selectedProfile.value!!.language
            _profileTheme.value = _selectedProfile.value!!.theme
        }
    }

    fun getProfileAmount() {
        viewModelScope.launch {
            repository.getProfileAmount(profileId = PROFILE_ID).collect { amount ->
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
    var categoryId: MutableState<Int> = mutableStateOf(0)
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

    fun updateCategory(
        categoryModel: CategoryModel
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryCategory.updateCategory(categoryModel = categoryModel)
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

}