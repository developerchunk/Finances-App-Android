package com.developerstring.financesapp.di

import android.content.Context
import androidx.room.Room
import com.developerstring.financesapp.roomdatabase.database.TransactionDatabase
import com.developerstring.financesapp.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Singleton

@Module
@InstallIn(Singleton::class)
class TransactionDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ActivityContext context: Context
    ) = Room.databaseBuilder(
        context,
        TransactionDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: TransactionDatabase) = database.transactionDao()

}