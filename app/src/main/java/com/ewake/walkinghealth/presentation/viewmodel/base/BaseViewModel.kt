package com.ewake.walkinghealth.presentation.viewmodel.base

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.ewake.walkinghealth.presentation.app.App
import com.ewake.walkinghealth.presentation.helper.SingleEventLiveData

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
open class BaseViewModel(app: App) : AndroidViewModel(app) {

    protected val _messageLiveData = SingleEventLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    protected val _navigationLiveData = SingleEventLiveData<NavDirections>()
    val navigationLiveData: LiveData<NavDirections> = _navigationLiveData

    protected val _navigateBackLiveData = SingleEventLiveData<Unit>()
    val navigateBackLiveData: LiveData<Unit> = _navigateBackLiveData

    private var isStart = false

    fun start() {
        if (!isStart) {
            onStart()
        }
    }

    open fun onStart() {
        // NO IMPL
    }
}