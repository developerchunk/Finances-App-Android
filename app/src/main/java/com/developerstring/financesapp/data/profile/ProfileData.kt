package com.developerstring.financesapp.data.profile

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.developerstring.financesapp.sharedviewmodel.ProfileViewModel

@Composable
fun ProfileData(
    context: Context,
    profileViewModel: ProfileViewModel,
    profile: (ProfileDataClass) -> Unit
) {
    profileViewModel.getProfileDetails(context = context)
    val profileName = profileViewModel.profileName.collectAsState()
    val profileTotalAmount = profileViewModel.profileTotalAmount.collectAsState()
    val profileCurrency = profileViewModel.profileCurrency.collectAsState()
    val profileSpending = profileViewModel.profileSpending.collectAsState()
    val profileSaving = profileViewModel.profileSavings.collectAsState()

    profile(
        ProfileDataClass(
            name = profileName.value,
            total_amount = profileTotalAmount.value,
            currency = profileCurrency.value,
            spending = profileSpending.value,
            savings = profileSaving.value
        )
    )
}