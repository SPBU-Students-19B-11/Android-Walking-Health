package com.ewake.walkinghealth.presentation.viewmodel.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ewake.walkinghealth.data.api.model.response.onSuccess
import com.ewake.walkinghealth.domain.usecase.DoctorsUseCase
import com.ewake.walkinghealth.domain.usecase.RegisterUseCase
import com.ewake.walkinghealth.presentation.app.App
import com.ewake.walkinghealth.presentation.model.RegistrationModel
import com.ewake.walkinghealth.presentation.model.SimpleUserModel
import com.ewake.walkinghealth.presentation.ui.fragment.register.RegisterFragmentDirections
import com.ewake.walkinghealth.presentation.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
@HiltViewModel
class RegisterViewModel @Inject constructor(
    app: App,
    private val doctorsUseCase: DoctorsUseCase,
    private val registerUseCase: RegisterUseCase
) : BaseViewModel(app) {

    private val _doctorsLiveData = MutableLiveData<MutableList<String>>()
    val doctorsLiveData: LiveData<MutableList<String>> = _doctorsLiveData

    private var doctors: List<SimpleUserModel> = listOf()

    private fun loadData() {
        viewModelScope.launch {
            loadDoctors()
        }
    }

    private suspend fun loadDoctors() {
        val response = doctorsUseCase.invoke()

        response.onSuccess { result ->
            val doctorsResult = result?.map {
                SimpleUserModel(login = it.login, fullname = it.fullname)
            }

            doctorsResult?.let {
                doctors = it
            }

            _doctorsLiveData.postValue(doctors.map { it.fullname }.toMutableList())
        }
    }

    fun onRegisterButtonClicked(screenData: RegistrationModel) {

        if (!screenData.isDoctor) {
            val doctorModel = getDoctorByFullname(screenData.selectedDoctor?.fullname)

            if (doctorModel != null) {
                screenData.selectedDoctor = doctorModel
            } else {
                _messageLiveData.postValue("Доктор с выбранным ФИО не найден")
            }
        } else {
            screenData.selectedDoctor = null
        }

        viewModelScope.launch {
            register(screenData)
        }
    }

    private suspend fun register(model: RegistrationModel) {
        registerUseCase(
            model.login,
            model.password,
            model.fullname,
            model.selectedDoctor?.login
        ).onSuccess {
            if (it != null) {
                _messageLiveData.postValue("Вы успешно зарегистрированы")
                _navigationLiveData.postValue(RegisterFragmentDirections.actionRegisterFragmentToProfileFragment())
            }
        }
    }

    private fun getDoctorByFullname(fullname: String?): SimpleUserModel? =
        doctors.find { it.fullname == fullname }

    override fun onStart() {
        loadData()
    }
}