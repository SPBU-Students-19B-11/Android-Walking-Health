package com.ewake.walkinghealth.presentation.viewmodel.login

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ewake.walkinghealth.domain.usecase.LoginUseCase
import com.ewake.walkinghealth.presentation.app.App
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
    private val loginUseCase: LoginUseCase
) : BaseViewModel(app) {

    fun onLoginButtonClicked(login: String, password: String) {
        viewModelScope.launch {
            login(login, password)
        }
    }

    private suspend fun login(login: String, password: String) {
        val response = loginUseCase.invoke(login, password)

        if (response.code == HttpURLConnection.HTTP_OK && response.result != null) {
            _messageLiveData.postValue(response.message)
            _navigationLiveData.postValue(LoginFragmentDirections.actionLoginFragmentToProfileFragment())
        }
    }

    fun onRegisterButtonClicked() {
        _navigationLiveData.postValue(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
    }

    fun onForgetPasswordClicked() {
        _messageLiveData.postValue("Будет реализовано позже")
    }
}