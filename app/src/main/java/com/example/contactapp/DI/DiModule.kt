package com.example.contactapp.DI

import android.app.Application
import androidx.room.Room
import com.example.contactapp.DATABASE_NAME
import com.example.contactapp.data.database.ContactAppDatabase
import com.example.contactapp.data.repo.Repo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DiModule {

    @Provides
    @Singleton
    fun provideContactDatabase(application: Application): ContactAppDatabase {
        return Room.databaseBuilder(
            application,
            ContactAppDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideContactRepository(database: ContactAppDatabase): Repo {
        return Repo(database)
    }
}