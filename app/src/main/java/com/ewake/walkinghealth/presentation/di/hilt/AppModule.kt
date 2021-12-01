package com.ewake.walkinghealth.presentation.di.hilt

import android.content.Context
import androidx.room.Room
import com.ewake.walkinghealth.data.api.Api
import com.ewake.walkinghealth.data.api.interceptors.UserDataInterceptor
import com.ewake.walkinghealth.data.local.prefs.BroadcastPrefs
import com.ewake.walkinghealth.data.local.prefs.UserDataPrefs
import com.ewake.walkinghealth.data.local.room.AppDatabase
import com.ewake.walkinghealth.presentation.broadcastreceiver.UserActivityReceiver
import com.ewake.walkinghealth.presentation.manager.ServiceStartingManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideOkHttp(userDataInterceptor: UserDataInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(userDataInterceptor)
            .addInterceptor(loggingInterceptor)

        return builder.build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://WalkingHealth.pythonanywhere.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi).withNullSerialization())
            .build()
    }

    @Provides
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "database.db").build()
    }

    @Provides
    @Singleton
    fun provideUserActivityReceiver(appDatabase: AppDatabase, userDataPrefs: UserDataPrefs): UserActivityReceiver {
        return UserActivityReceiver(appDatabase, userDataPrefs)
    }

    @Provides
    @Singleton
    fun provideServiceStartingManager(receiver: UserActivityReceiver, prefs: BroadcastPrefs): ServiceStartingManager {
        return ServiceStartingManager(receiver, prefs)
    }
}