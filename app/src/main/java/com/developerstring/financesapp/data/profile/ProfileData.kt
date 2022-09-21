package com.developerstring.financesapp.data.profile

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.developerstring.financesapp.sharedviewmodel.SharedViewModel

@Composable
fun ProfileData(
    context: Context,
    sharedViewModel: SharedViewModel,
    profile: (ProfileDataClass) -> Unit
) {
    sharedViewModel.getProfileDetails(context = context)
    val profileName = sharedViewModel.profileName.collectAsState()
    val profileTotalAmount = sharedViewModel.profileTotalAmount.collectAsState()
    val profileCurrency = sharedViewModel.profileCurrency.collectAsState()
    val profileSpending = sharedViewModel.profileSpending.collectAsState()
    val profileSaving = sharedViewModel.profileSavings.collectAsState()

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