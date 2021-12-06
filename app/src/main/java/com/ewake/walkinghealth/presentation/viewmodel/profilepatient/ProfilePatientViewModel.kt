package com.ewake.walkinghealth.presentation.viewmodel.profilepatient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ewake.walkinghealth.data.api.model.response.MedicalGetDataResult
import com.ewake.walkinghealth.data.api.model.response.onFailure
import com.ewake.walkinghealth.data.api.model.response.onSuccess
import com.ewake.walkinghealth.data.local.room.entity.UserActivityData
import com.ewake.walkinghealth.data.local.room.entity.UserActivityEntity
import com.ewake.walkinghealth.domain.repository.medical.MedicalRepository
import com.ewake.walkinghealth.domain.usecase.MedicalGetDataUseCase
import com.ewake.walkinghealth.domain.usecase.UserDataUseCase
import com.ewake.walkinghealth.presentation.app.App
import com.ewake.walkinghealth.presentation.model.SimpleUserModel
import com.ewake.walkinghealth.presentation.model.UserDataModel
import com.ewake.walkinghealth.presentation.ui.fragment.profilepatient.ProfilePatientFragmentDirections
import com.ewake.walkinghealth.presentation.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
@HiltViewModel
class ProfilePatientViewModel @Inject constructor(
    private val userDataUseCase: UserDataUseCase,
    private val medicalGetDataUseCase: MedicalGetDataUseCase,
    private val medicalRepository: MedicalRepository
) : BaseViewModel() {

    private val _userDataLiveData = MutableLiveData<UserDataModel>()
    val userDataLiveData: LiveData<UserDataModel> = _userDataLiveData

    private val _userActivityLiveData = MutableLiveData<List<UserActivityData>>()
    val userActivityLiveData: LiveData<List<UserActivityData>> = _userActivityLiveData

    private val _datesLiveData = MutableLiveData<List<String>>()
    val datesLiveData: LiveData<List<String>> = _datesLiveData

    private var userData = UserDataModel()

    var login: String? = null

    private suspend fun loadUserData() {
        val response = userDataUseCase(login)

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

    override fun onStart() {
        viewModelScope.launch {
            loadUserData()
            loadDates()
        }
    }

    fun onMessagesClicked() {
        val action = ProfilePatientFragmentDirections.actionProfileFragmentToMessagesFragment(
            userData.login,
            userData.doctor?.fullname ?: ""
        )
        _navigationLiveData.postValue(action)
    }

    fun onDateChosen(date: String) {
        viewModelScope.launch {
            loadUserActivities(date)
        }
    }

    private suspend fun loadDates() {
        medicalRepository.getDates(login!!).onSuccess { list ->
            list?.let { _datesLiveData.postValue(it) }
        }
    }

    private suspend fun loadUserActivities(date: String) {
        medicalGetDataUseCase.invoke(login, date).onSuccess { result ->
            if (result != null) {
                _userActivityLiveData.postValue(result.data)
            }
        }
    }
}