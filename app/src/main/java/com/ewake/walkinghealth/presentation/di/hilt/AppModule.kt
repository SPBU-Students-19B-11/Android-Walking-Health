package com.ewake.walkinghealth.presentation.di.hilt

import com.ewake.walkinghealth.data.api.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideOkHttp(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(loggingInterceptor)

        return builder.build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
                // TODO Добавить реальный URL
            .baseUrl("https://github.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }
}