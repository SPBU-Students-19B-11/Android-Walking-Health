package com.ewake.walkinghealth.presentation.viewmodel.login

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ewake.walkinghealth.data.local.prefs.UserDataPrefs
import com.ewake.walkinghealth.domain.usecase.LoginUseCase
import com.ewake.walkinghealth.domain.usecase.SaveUserLoginUseCase
import com.ewake.walkinghealth.domain.usecase.SaveUserTokenUseCase
import com.ewake.walkinghealth.presentation.app.App
import com.ewake.walkinghealth.presentation.helper.SingleEventLiveData
import com.ewake.walkinghealth.presentation.ui.fragment.login.LoginFragmentDirections
import com.ewake.walkinghealth.presentation.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import javax.inject.Inject

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    app: App,
    private val loginUseCase: LoginUseCase,
    private val userDataPrefs: UserDataPrefs
) : BaseViewModel(app) {

    private val _startServicesLiveData = SingleEventLiveData<Unit>()
    val startServicesLiveData: LiveData<Unit> = _startServicesLiveData

    fun onLoginButtonClicked(login: String, password: String) {
        viewModelScope.launch {
            login(login, password)
        }
    }

    private suspend fun login(login: String, password: String) {
        val response = loginUseCase.invoke(login, password)

        if (response.code == HttpURLConnection.HTTP_OK && response.result != null) {
            response.result?.let {
                userDataPrefs.isDoctor= it.isDoctor
                userDataPrefs.login = it.login
                userDataPrefs.token = it.token

                _startServicesLiveData.postValue(Unit)
                _navigationLiveData.postValue(
                    if (it.isDoctor) {
                        LoginFragmentDirections.actionLoginFragmentToProfileDoctorFragment()
                    } else {
                        LoginFragmentDirections.actionLoginFragmentToProfileFragment()
                    }
                )
            }

            _messageLiveData.postValue(response.message)


        }
    }

    fun onRegisterButtonClicked() {
        _navigationLiveData.postValue(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
    }

    fun onForgetPasswordClicked() {
        _messageLiveData.postValue("Будет реализовано позже")
    }
}