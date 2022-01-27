package com.hussien.quoty.di

import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetManager
import androidx.preference.PreferenceManager
import androidx.room.Room
import androidx.work.WorkManager
import com.hussien.quoty.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "QuotesDatabase"
        ).createFromAsset("databases/quotes.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideDao(db: AppDatabase) = db.quotesDao

    @Singleton
    @Provides
    @ApplicationScope
    fun provideCoroutineAppScope() = CoroutineScope(SupervisorJob())

    @IODispatcher
    @Provides
    @Singleton
    fun provideIoDispatcher() = Dispatchers.IO

    @UIDispatcher
    @Singleton
    @Provides
    fun provideUiDispatcher() = Dispatchers.Main

    @Singleton
    @Provides
    fun provideDefaultSharedPref(@ApplicationContext context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    fun provideAssetManager(@ApplicationContext context: Context): AssetManager = context.resources.assets

    @Provides
    fun provideWorkManager(@ApplicationContext context: Context) = WorkManager.getInstance(context)
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class IODispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class UIDispatcher