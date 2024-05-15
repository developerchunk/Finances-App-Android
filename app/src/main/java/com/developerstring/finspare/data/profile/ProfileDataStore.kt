package com.developerstring.finspare.data.profile

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.developerstring.finspare.util.Constants.MESSAGE_ID_LAST_SCAN_KEY
import com.developerstring.finspare.util.Constants.MESSAGE_SCAN_ENABLE_KEY
import com.developerstring.finspare.util.Constants.NO
import com.developerstring.finspare.util.Constants.PROFILE_CREATED_STATUS_KEY
import com.developerstring.finspare.util.Constants.PROFILE_DATA
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProfileDataStore(private val context: Context) {

    // to make sure there is only one data instance
    companion object {
        private val Context.dataStore_: DataStore<Preferences> by preferencesDataStore(PROFILE_DATA)
        val PROFILE_CREATED_STATUS = stringPreferencesKey(PROFILE_CREATED_STATUS_KEY)
        val MESSAGE_SCAN_ENABLE = booleanPreferencesKey(MESSAGE_SCAN_ENABLE_KEY)
//        val MESSAGE_SCAN_DATE = stringPreferencesKey(MESSAGE_LAST_SCAN_KEY)
        val MESSAGE_SCAN_ID = stringPreferencesKey(MESSAGE_ID_LAST_SCAN_KEY)
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
//    val getMessageScanEnable: Flow<Boolean?> = context.dataStore_.data
//        .map { preferences ->
//            preferences[MESSAGE_SCAN_ENABLE] ?: false
//        }

    // save profileCreatedStatus into dataStore
    suspend fun saveMessageScanEnable(enable: Boolean) {
        context.dataStore_.edit { preferences ->
            preferences[MESSAGE_SCAN_ENABLE] = enable
        }
    }

//    val getMessageScanDate: Flow<String?> = context.dataStore_.data
//        .map { preferences ->
//            preferences[MESSAGE_SCAN_DATE] ?: NO
//        }

    // save profileCreatedStatus into dataStore
//    suspend fun saveMessageScanDate(value: String) {
//        context.dataStore_.edit { preferences ->
//            preferences[MESSAGE_SCAN_DATE] = value
//        }
//    }

    // get saved profileCreatedStatus
    val getMessageScanIDs: Flow<String?> = context.dataStore_.data
        .map { preferences ->
            preferences[MESSAGE_SCAN_ID] ?: emptyList<String>().toString()
        }

    // save profileCreatedStatus into dataStore
    suspend fun saveMessageScanIDs(value: String) {
        context.dataStore_.edit { preferences ->
            preferences[MESSAGE_SCAN_ID] = value
        }
    }
}