package com.developerstring.financesapp.roomdatabase.repository

import com.developerstring.financesapp.roomdatabase.dao.ProfileDao
import com.developerstring.financesapp.roomdatabase.models.ProfileModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ProfileRepository @Inject constructor(private val profileDao: ProfileDao) {

    fun getSelectedProfile(profileId: Int): Flow<ProfileModel?> {
        return profileDao.getSelectedProfile(profileId = profileId)
    }

    fun getProfileAmount(profileId: Int): Flow<Int?> {
        return profileDao.getProfileAmount(profileId = profileId)
    }

    suspend fun addProfile(profileModel: ProfileModel) {
        profileDao.addProfile(profileModel = profileModel)
    }

    suspend fun updateProfile(profileModel: ProfileModel) {
        profileDao.updateProfileData(profileModel = profileModel)
    }

    suspend fun updateProfileAmount(profileId: Int, amount: Int) {
        profileDao.updateProfileAmount(profileId = profileId, amount = amount)
    }

    suspend fun updateProfileTheme(profileId: Int, theme: String) {
        profileDao.updateProfileTheme(profileId = profileId, theme = theme)
    }

    suspend fun updateProfileLanguage(profileId: Int, language: String) {
        profileDao.updateProfileLanguage(profileId = profileId, language = language)
    }

}