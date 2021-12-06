package com.ewake.walkinghealth.presentation.ui.fragment.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.ewake.walkinghealth.databinding.FragmentSplashBinding
import com.ewake.walkinghealth.presentation.manager.ServiceStartingManager
import com.ewake.walkinghealth.presentation.viewmodel.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment: Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding: FragmentSplashBinding
        get() = _binding!!

    private val viewModel by viewModels<SplashViewModel>()

    @Inject
    lateinit var serviceStartingManager: ServiceStartingManager

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
        activity?.let { serviceStartingManager.startServices(it) }
        serviceStartingManager.sendData(requireContext())
    }
}