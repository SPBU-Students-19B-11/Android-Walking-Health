package com.ewake.walkinghealth.presentation.viewmodel.messages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ewake.walkinghealth.data.api.model.request.SendMessageRequest
import com.ewake.walkinghealth.data.api.model.response.onFailure
import com.ewake.walkinghealth.data.api.model.response.onSuccess
import com.ewake.walkinghealth.data.local.prefs.UserDataPrefs
import com.ewake.walkinghealth.domain.repository.medical.MedicalRepository
import com.ewake.walkinghealth.domain.usecase.GetUserLoginUseCase
import com.ewake.walkinghealth.domain.usecase.MessagesUseCase
import com.ewake.walkinghealth.presentation.app.App
import com.ewake.walkinghealth.presentation.helper.SingleEventLiveData
import com.ewake.walkinghealth.presentation.model.MessageModel
import com.ewake.walkinghealth.presentation.model.SimpleUserModel
import com.ewake.walkinghealth.presentation.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
@HiltViewModel
class MessagesViewModel @Inject constructor(
    private val messagesUseCase: MessagesUseCase,
    private val medicalRepository: MedicalRepository,
    private val userDataPrefs: UserDataPrefs
) : BaseViewModel() {

    private val _messagesLiveData = MutableLiveData<MutableList<MessageModel>>()
    val messagesLiveData: LiveData<MutableList<MessageModel>> = _messagesLiveData

    private val _isDoctorLiveData = SingleEventLiveData<Boolean>()
    val isDoctorLiveData: LiveData<Boolean> = _isDoctorLiveData

    var login: String = ""
    var doctorFullname: String = ""

    private var messages = mutableListOf<MessageModel>()

    private suspend fun loadMessages() {
        val response = messagesUseCase(login)

        response.onSuccess { result ->
            if (result != null) {
                messages = result.map {
                    MessageModel(message = it.message, timestamp = it.timestamp)
                }.toMutableList()

                _messagesLiveData.postValue(messages)
            }
        }.onFailure {
            _messageLiveData.postValue(it)
        }
    }

    override fun onStart() {
        viewModelScope.launch {
            _isDoctorLiveData.postValue(userDataPrefs.isDoctor)
            loadMessages()
        }
    }

    fun onMessageSendClicked(messageText: String) {
        viewModelScope.launch {
            sendMessage(messageText)
        }
    }

    private suspend fun sendMessage(message: String) {
        val response = medicalRepository.sendMessage(SendMessageRequest(message = message, patientLogin = login))

        response.onSuccess {
            if (it != null) {
                val messageModel = MessageModel(message = it.message, timestamp = it.timestamp)

                messages.add(messageModel)
                _messagesLiveData.postValue(messages)
            }
        }
    }
}