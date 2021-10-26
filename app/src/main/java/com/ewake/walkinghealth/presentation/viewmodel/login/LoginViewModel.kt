package com.ewake.walkinghealth.presentation.viewmodel.login

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.ewake.walkinghealth.presentation.app.App
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
@HiltViewModel
class LoginViewModel @Inject constructor(app: App) : AndroidViewModel(app) {
}