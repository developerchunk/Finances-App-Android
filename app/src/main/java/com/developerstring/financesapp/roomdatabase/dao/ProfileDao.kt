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

    @Query("UPDATE profile_table SET total_amount=:amount WHERE id=:profileId")
    suspend fun updateProfileAmount(profileId: Int,amount: Int)

    @Query("UPDATE profile_table SET theme=:theme WHERE id=:profileId")
    suspend fun updateProfileTheme(profileId: Int,theme: String)

    @Query("UPDATE profile_table SET language=:language WHERE id=:profileId")
    suspend fun updateProfileLanguage(profileId: Int,language: String)

}