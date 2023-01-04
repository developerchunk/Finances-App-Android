package com.developerstring.financesapp.di

import android.content.Context
import androidx.room.Room
import com.developerstring.financesapp.roomdatabase.database.TransactionDatabase
import com.developerstring.financesapp.util.Constants.TRANSACTION_DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TransactionDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        TransactionDatabase::class.java,
        TRANSACTION_DB_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: TransactionDatabase) = database.transactionDao()

    @Singleton
    @Provides
    fun provideProfileDao(database: TransactionDatabase) = database.profileDao()

    @Singleton
    @Provides
    fun provideCategoryDao(database: TransactionDatabase) = database.categoryDao()

}