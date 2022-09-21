package com.developerstring.financesapp.data.profile

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.developerstring.financesapp.util.Constants.NO
import com.developerstring.financesapp.util.Constants.PROFILE_TOTAL_AMOUNT_KEY
import com.developerstring.financesapp.util.Constants.PROFILE_CREATED_STATUS_KEY
import com.developerstring.financesapp.util.Constants.PROFILE_CURRENCY_KEY
import com.developerstring.financesapp.util.Constants.PROFILE_DATA
import com.developerstring.financesapp.util.Constants.PROFILE_MONTHLY_SAVINGS_KEY
import com.developerstring.financesapp.util.Constants.PROFILE_MONTHLY_SPENDING_KEY
import com.developerstring.financesapp.util.Constants.PROFILE_NAME_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProfileDataStore(private val context: Context) {

    // to make sure there is only one data instance
    companion object {
        private val Context.dataStore_: DataStore<Preferences> by preferencesDataStore(PROFILE_DATA)
        val NAME_KEY = stringPreferencesKey(PROFILE_NAME_KEY)
        val TOTAL_AMOUNT_KEY = intPreferencesKey(PROFILE_TOTAL_AMOUNT_KEY)
        val CURRENCY_KEY = stringPreferencesKey(PROFILE_CURRENCY_KEY)
        val MONTHLY_SPENDING_KEY = intPreferencesKey(PROFILE_MONTHLY_SPENDING_KEY)
        val MONTHLY_SAVINGS_KEY = intPreferencesKey(PROFILE_MONTHLY_SAVINGS_KEY)
        val PROFILE_CREATED_STATUS = stringPreferencesKey(PROFILE_CREATED_STATUS_KEY)
    }

    // for Name
    // get saved name
    val getName: Flow<String?> = context.dataStore_.data
        .map { preferences ->
            preferences[NAME_KEY] ?: ""
        }

    // save name into dataStore
    suspend fun saveName(value: String) {
        context.dataStore_.edit { preferences ->
            preferences[NAME_KEY] = value
        }
    }

    // for Amount
    // get saved total amount
    val getTotalAmount: Flow<Int?> = context.dataStore_.data
        .map { preferences ->
            preferences[TOTAL_AMOUNT_KEY] ?: 0
        }

    // save amount into dataStore
    suspend fun saveTotalAmount(value: Int) {
        context.dataStore_.edit { preferences ->
            preferences[TOTAL_AMOUNT_KEY] = value
        }
    }

    // for Currency
    // get saved currency
    val getCurrency: Flow<String?> = context.dataStore_.data
        .map { preferences ->
            preferences[CURRENCY_KEY] ?: ""
        }

    // save currency into dataStore
    suspend fun saveCurrency(value: String) {
        context.dataStore_.edit { preferences ->
            preferences[CURRENCY_KEY] = value
        }
    }

    // get saved monthlySpending
    val getMonthlySpending: Flow<Int?> = context.dataStore_.data
        .map { preferences ->
            preferences[MONTHLY_SPENDING_KEY] ?: 0
        }

    // save MonthlySpending into dataStore
    suspend fun saveMonthlySpending(value: Int) {
        context.dataStore_.edit { preferences ->
            preferences[MONTHLY_SPENDING_KEY] = value
        }
    }

    // get saved monthlySpending
    val getMonthlySavings: Flow<Int?> = context.dataStore_.data
        .map { preferences ->
            preferences[MONTHLY_SAVINGS_KEY] ?: 0
        }

    // save MonthlySpending into dataStore
    suspend fun saveMonthlySavings(value: Int) {
        context.dataStore_.edit { preferences ->
            preferences[MONTHLY_SAVINGS_KEY] = value
        }
    }

    // get saved profileCreatedStatus
    val getProfileCreatedState: Flow<String?> = context.dataStore_.data
        .map { preferences ->
            preferences[PROFILE_CREATED_STATUS] ?: NO
        }

    // save profileCreatedStatus into dataStore
    suspend fun saveProfileCreatedStatus(value: String) {
        context.dataStore_.edit { preferences ->
            preferences[PROFILE_CREATED_STATUS] = value
        }
    }
}