package com.ewake.walkinghealth.presentation.viewmodel.messages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ewake.walkinghealth.data.api.model.response.onFailure
import com.ewake.walkinghealth.data.api.model.response.onSuccess
import com.ewake.walkinghealth.domain.usecase.GetUserLoginUseCase
import com.ewake.walkinghealth.domain.usecase.MessagesUseCase
import com.ewake.walkinghealth.presentation.app.App
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
    app: App,
    private val messagesUseCase: MessagesUseCase,
    private val getUserLoginUseCase: GetUserLoginUseCase
) : BaseViewModel(app) {

    private val _messagesLiveData = MutableLiveData<List<MessageModel>>()
    val messagesLiveData: LiveData<List<MessageModel>> = _messagesLiveData

    private var userModel: SimpleUserModel? = null

    private suspend fun loadMessages() {
        val response = if (userModel != null) {
            messagesUseCase(userModel!!.login)
        } else {
            messagesUseCase(getCurrentUserId())
        }

        response.onSuccess { result ->
            if (result != null) {
                _messagesLiveData.postValue(
                    result.map {
                        MessageModel(id = it.id, message = it.message, timestamp = it.timestamp)
                    }
                )
            }
        }.onFailure {
            _messageLiveData.postValue(it)
        }
    }

    private suspend fun getCurrentUserId(): String {
        return getUserLoginUseCase() ?: ""
    }

    override fun onStart() {
        viewModelScope.launch {
            loadMessages()
        }
    }
}