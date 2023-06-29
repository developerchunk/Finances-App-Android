package com.developerstring.finspare.roomdatabase.dao

import androidx.room.*
import com.developerstring.finspare.roomdatabase.models.ProfileModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {

    @Query("SELECT * FROM profile_table")
    fun getAllProfile(): Flow<List<ProfileModel>>

    @Query("SELECT * FROM profile_table WHERE id=:profileId")
    fun getSelectedProfile(profileId: Int): Flow<ProfileModel>

    @Query("SELECT total_amount FROM profile_table WHERE id=:profileId")
    fun getProfileAmount(profileId: Int): Flow<Int>

    @Query("SELECT time24Hours FROM profile_table WHERE id=:profileId")
    fun getTime24Hours(profileId: Int): Flow<Boolean>

    @Query("UPDATE profile_table SET time24Hours=:time24Hours WHERE id=:profileId")
    suspend fun updateTime24Hours(profileId: Int,time24Hours: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProfile(profileModel: ProfileModel)

    @Update
    suspend fun updateProfileData(profileModel: ProfileModel)

    @Query("UPDATE profile_table SET total_amount=:amount WHERE id=:profileId")
    suspend fun updateProfileAmount(profileId: Int,amount: Int)

    @Query("UPDATE profile_table SET theme=:theme WHERE id=:profileId")
    suspend fun updateProfileTheme(profileId: Int,theme: String)

    @Query("UPDATE profile_table SET language=:language WHERE id=:profileId")
    suspend fun updateProfileLanguage(profileId: Int,language: String)

    @Query("DELETE FROM profile_table WHERE id=:profileId")
    suspend fun deleteProfile(profileId: Int)

    @Query("DELETE FROM profile_table")
    suspend fun deleteAllProfiles()

    @Query("UPDATE profile_table SET total_amount=:amount, amount_type=:amountType WHERE id=:profileId")
    suspend fun updateContactAmount(amount: Int, amountType: String, profileId: Int)

}