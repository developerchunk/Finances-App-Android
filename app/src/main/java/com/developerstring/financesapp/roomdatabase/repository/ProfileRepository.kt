package com.developerstring.financesapp.roomdatabase.repository

import com.developerstring.financesapp.roomdatabase.dao.ProfileDao
import com.developerstring.financesapp.roomdatabase.models.ProfileModel
import com.developerstring.financesapp.roomdatabase.models.TransactionModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ProfileRepository @Inject constructor(private val profileDao: ProfileDao) {

    fun getSelectedProfile(profileId: Int): Flow<ProfileModel> {
        return profileDao.getSelectedProfile(profileId = profileId)
    }

    suspend fun addProfile(profileModel: ProfileModel) {
        profileDao.addProfile(profileModel = profileModel)
    }

    suspend fun updateProfile(profileModel: ProfileModel) {
        profileDao.updateProfileData(profileModel = profileModel)
    }

}