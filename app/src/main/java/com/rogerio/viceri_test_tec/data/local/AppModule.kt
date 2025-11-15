package com.rogerio.viceri_test_tec.data.local

import android.content.Context
import androidx.room.Room
import com.rogerio.viceri_test_tec.data.repository.UserRepository
import com.rogerio.viceri_test_tec.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // --- ROOM DATABASE ---
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "users_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    // --- REPOSITORY ---
    @Provides
    @Singleton
    fun provideUserRepository(api: ApiService, dao: UserDao): UserRepository {
        return UserRepository(api, dao)
    }
}
