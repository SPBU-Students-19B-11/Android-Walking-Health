package com.ewake.walkinghealth.presentation.di.hilt

import com.ewake.walkinghealth.data.api.Api
import com.ewake.walkinghealth.data.local.prefs.UserDataPrefs
import com.ewake.walkinghealth.domain.repository.impl.MedicalRepositoryImpl
import com.ewake.walkinghealth.domain.repository.impl.UserRepositoryImpl
import com.ewake.walkinghealth.domain.repository.medical.MedicalRepository
import com.ewake.walkinghealth.domain.repository.user.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    fun provideUserRepository(api: Api, userDataPrefs: UserDataPrefs): UserRepository =
        UserRepositoryImpl(api, userDataPrefs)

    @Provides
    fun provideMedicalRepository(api: Api): MedicalRepository = MedicalRepositoryImpl(api)
}