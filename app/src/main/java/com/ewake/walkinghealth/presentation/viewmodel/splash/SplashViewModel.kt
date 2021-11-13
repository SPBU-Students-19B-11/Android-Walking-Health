package com.ewake.walkinghealth.presentation.viewmodel.splash

import androidx.lifecycle.LiveData
import com.ewake.walkinghealth.data.local.prefs.UserDataPrefs
import com.ewake.walkinghealth.presentation.app.App
import com.ewake.walkinghealth.presentation.helper.SingleEventLiveData
import com.ewake.walkinghealth.presentation.ui.fragment.splash.SplashFragmentDirections
import com.ewake.walkinghealth.presentation.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    app: App,
    private val userDataPrefs: UserDataPrefs,
) : BaseViewModel(app) {

    private val _startServicesLiveData = SingleEventLiveData<Unit>()
    val startServicesLiveData: LiveData<Unit> = _startServicesLiveData

    override fun onStart() {
        checkIsLogin()
    }

    private fun checkIsLogin() {
        _navigationLiveData.postValue(
            when {
                userDataPrefs.login == null -> {
                    SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                }
                userDataPrefs.isDoctor -> {
                    _startServicesLiveData.postValue(Unit)
                    SplashFragmentDirections.actionSplashFragmentToProfileDoctorFragment()
                }
                else -> {
                    _startServicesLiveData.postValue(Unit)
                    SplashFragmentDirections.actionSplashFragmentToProfileFragment(userDataPrefs.login)
                }
            }
        )
    }
}