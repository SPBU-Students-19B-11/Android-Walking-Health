package com.ewake.walkinghealth.presentation.viewmodel.profiledoctor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ewake.walkinghealth.data.api.model.response.onFailure
import com.ewake.walkinghealth.data.api.model.response.onSuccess
import com.ewake.walkinghealth.data.local.prefs.UserDataPrefs
import com.ewake.walkinghealth.domain.usecase.UserDataUseCase
import com.ewake.walkinghealth.presentation.app.App
import com.ewake.walkinghealth.presentation.model.SimpleUserModel
import com.ewake.walkinghealth.presentation.model.UserDataModel
import com.ewake.walkinghealth.presentation.ui.fragment.profiledoctor.ProfileDoctorFragmentDirections
import com.ewake.walkinghealth.presentation.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileDoctorViewModel @Inject constructor(
    private val userDataUseCase: UserDataUseCase,
    private val userDataPrefs: UserDataPrefs
) : BaseViewModel() {

    private val _userDataLiveData = MutableLiveData<UserDataModel>()
    val userDataLiveData: LiveData<UserDataModel> = _userDataLiveData

    private var userData = UserDataModel()

    private suspend fun loadUserData() {
        val response = userDataUseCase(userDataPrefs.login ?: "")

        response.onSuccess {
            if (it != null) {
                userData = UserDataModel(
                    login = it.login,
                    fullname = it.fullname,
                    isDoctor = it.isDoctor,
                    doctor = if (it.doctor != null) SimpleUserModel(
                        it.doctor!!.login,
                        it.doctor!!.fullname
                    ) else null,
                    patients = it.patients?.map { model ->
                        SimpleUserModel(model.login, model.fullname)
                    }
                )
                _userDataLiveData.postValue(
                    userData
                )
            }
        }.onFailure {
            _messageLiveData.postValue(it)
        }
    }

    fun exit() {
        userDataPrefs.token = null
        userDataPrefs.login = null
        _navigationLiveData.postValue(ProfileDoctorFragmentDirections.actionProfileDoctorFragmentToSplashFragment())
    }

    fun onPatientClicked(model: SimpleUserModel) {
        _navigationLiveData.postValue(
            ProfileDoctorFragmentDirections.actionProfileDoctorFragmentToProfileFragment(model.login)
        )
    }

    override fun onStart() {
        viewModelScope.launch {
            loadUserData()
        }
    }

}