package com.developerstring.financesapp.roomdatabase.dao

import androidx.room.*
import com.developerstring.financesapp.roomdatabase.models.ProfileModel
import com.developerstring.financesapp.roomdatabase.models.TransactionModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {

    @Query("SELECT * FROM profile_table WHERE id=:profileId")
    fun getSelectedProfile(profileId: Int): Flow<ProfileModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProfile(profileModel: ProfileModel)

    @Update
    suspend fun updateProfileData(profileModel: ProfileModel)

}