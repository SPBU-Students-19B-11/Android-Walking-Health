package com.ewake.walkinghealth.presentation.viewmodel.profilepatient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ewake.walkinghealth.data.api.model.response.onFailure
import com.ewake.walkinghealth.data.api.model.response.onSuccess
import com.ewake.walkinghealth.domain.usecase.UserDataUseCase
import com.ewake.walkinghealth.presentation.app.App
import com.ewake.walkinghealth.presentation.model.SimpleUserModel
import com.ewake.walkinghealth.presentation.model.UserDataModel
import com.ewake.walkinghealth.presentation.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
@HiltViewModel
class ProfilePatientViewModel @Inject constructor(
    app: App,
    private val userDataUseCase: UserDataUseCase
) : BaseViewModel(app) {

    private val _userDataLiveData = MutableLiveData<UserDataModel>()
    val userDataLiveData: LiveData<UserDataModel> = _userDataLiveData

    private var userData = UserDataModel()

    private suspend fun loadUserData() {
        val response = userDataUseCase()

        response.onSuccess {
            if (it != null) {
                _userDataLiveData.postValue(
                    UserDataModel(
                        login = it.login,
                        fullname = it.fullname,
                        isDoctor = it.isDoctor,
                        doctor = if (it.doctorId != null) SimpleUserModel(
                            it.doctorId!!,
                            it.doctorId!!
                        ) else null,
                        patients = it.patients?.map { model ->
                            SimpleUserModel(model.login, model.fullname)
                        }
                    )
                )
            }
        }.onFailure {
            _messageLiveData.postValue(it)
        }
    }

    override fun onStart() {
        viewModelScope.launch {
            loadUserData()
        }
    }
}