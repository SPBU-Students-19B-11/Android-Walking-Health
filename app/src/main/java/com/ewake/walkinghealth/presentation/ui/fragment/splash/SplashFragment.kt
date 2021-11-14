package com.ewake.walkinghealth.presentation.ui.fragment.splash

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.ewake.walkinghealth.data.service.AccelerationService
import com.ewake.walkinghealth.data.service.StepCountingService
import com.ewake.walkinghealth.databinding.FragmentSplashBinding
import com.ewake.walkinghealth.presentation.broadcastreceiver.AccelerationReceiver
import com.ewake.walkinghealth.presentation.broadcastreceiver.StepsReceiver
import com.ewake.walkinghealth.presentation.viewmodel.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding: FragmentSplashBinding
        get() = _binding!!

    private val viewModel by viewModels<SplashViewModel>()

    @Inject
    lateinit var receiver: StepsReceiver
    @Inject
    lateinit var accelerationReceiver: AccelerationReceiver

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        viewModel.apply {
            start()
            navigationLiveData.observe(viewLifecycleOwner, ::navigate)
            startServicesLiveData.observe(viewLifecycleOwner, ::startServices)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigate(action: NavDirections) {
        findNavController().navigate(action)
    }

    private fun startServices(unit: Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity?.startForegroundService(Intent(activity?.baseContext, StepCountingService::class.java))
            activity?.startForegroundService(Intent(activity?.baseContext, AccelerationService::class.java))
        } else {
            activity?.startService(Intent(activity?.baseContext, StepCountingService::class.java))
            activity?.startService(Intent(activity?.baseContext, AccelerationService::class.java))
        }


        activity?.registerReceiver(receiver, IntentFilter(StepCountingService.STEP_COUNTING_SERVICE_TAG))
        activity?.registerReceiver(accelerationReceiver, IntentFilter(AccelerationService.ACCELERATION_SERVICE_TAG))
    }
}