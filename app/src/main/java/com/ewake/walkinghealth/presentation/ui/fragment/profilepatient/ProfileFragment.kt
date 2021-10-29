package com.ewake.walkinghealth.presentation.ui.fragment.profilepatient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ewake.walkinghealth.databinding.FragmentProfilePatientBinding
import com.ewake.walkinghealth.presentation.model.SimpleUserModel
import com.ewake.walkinghealth.presentation.model.UserDataModel
import com.ewake.walkinghealth.presentation.viewmodel.profilepatient.ProfileViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfilePatientBinding? = null
    private val binding: FragmentProfilePatientBinding
        get() = _binding!!

    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfilePatientBinding.inflate(inflater, container, false)

        viewModel.apply {
            userDataLiveData.observe(viewLifecycleOwner, ::setData)
            messageLiveData.observe(viewLifecycleOwner, ::showMessage)
            start()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setData(data: UserDataModel) {
        if (!data.isDoctor) {
            binding.apply {
                profileCard.model = SimpleUserModel(data.login, data.fullname)
                data.doctor?.let { doctor.model = it }
            }
        }
    }

    private fun showMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}