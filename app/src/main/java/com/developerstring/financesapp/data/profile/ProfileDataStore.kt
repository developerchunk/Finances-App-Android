package com.developerstring.financesapp.data.profile

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.developerstring.financesapp.util.Constants.NO
import com.developerstring.financesapp.util.Constants.PROFILE_TOTAL_AMOUNT_KEY
import com.developerstring.financesapp.util.Constants.PROFILE_CREATED_STATUS_KEY
import com.developerstring.financesapp.util.Constants.PROFILE_CURRENCY_KEY
import com.developerstring.financesapp.util.Constants.PROFILE_DATA
import com.developerstring.financesapp.util.Constants.PROFILE_MONTHLY_SAVINGS_KEY
import com.developerstring.financesapp.util.Constants.PROFILE_MONTHLY_SPENDING_KEY
import com.developerstring.financesapp.util.Constants.PROFILE_NAME_KEY
import com.developerstring.financesapp.util.Constants.THEME_SETTING_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProfileDataStore(private val context: Context) {

    // to make sure there is only one data instance
    companion object {
        private val Context.dataStore_: DataStore<Preferences> by preferencesDataStore(PROFILE_DATA)
        val PROFILE_CREATED_STATUS = stringPreferencesKey(PROFILE_CREATED_STATUS_KEY)
        val THEME_SETTING = booleanPreferencesKey(THEME_SETTING_KEY)

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

    // get saved profileCreatedStatus
    val getThemeSetting: Flow<Boolean?> = context.dataStore_.data
        .map { preferences ->
            preferences[THEME_SETTING] ?: true
        }

    // save profileCreatedStatus into dataStore
    suspend fun saveThemeSetting(value: Boolean) {
        context.dataStore_.edit { preferences ->
            preferences[THEME_SETTING] = value
        }
    }
}