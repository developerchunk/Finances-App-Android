package com.developerstring.financesapp.data.onboarding

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.developerstring.financesapp.util.Constants.ON_BOARDING_STATUS
import com.developerstring.financesapp.util.Constants.ON_BOARDING_STATUS_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OnBoardingStatus(private val context: Context) {
    // to make sure there is only one data instance
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(ON_BOARDING_STATUS)
        val ON_BOARDING_STATUS_KEY_ = stringPreferencesKey(ON_BOARDING_STATUS_KEY)
    }

    // get saved onBoardingStatus
    val getOnBoardingStatus: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[ON_BOARDING_STATUS_KEY_] ?: "NO"
        }

    // save onBoardingStatus into dataStore
    suspend fun saveOnBoardingStatus(value: String) {
        context.dataStore.edit { preferences ->
            preferences[ON_BOARDING_STATUS_KEY_] = value
        }
    }
}