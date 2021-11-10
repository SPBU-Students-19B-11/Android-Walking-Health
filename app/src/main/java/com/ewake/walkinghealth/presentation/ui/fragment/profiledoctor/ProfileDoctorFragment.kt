package com.ewake.walkinghealth.presentation.ui.fragment.profiledoctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ewake.walkinghealth.databinding.FragmentProfileDoctorBinding
import com.ewake.walkinghealth.presentation.model.UserDataModel
import com.ewake.walkinghealth.presentation.ui.fragment.profiledoctor.adapter.PatientsAdapter
import com.ewake.walkinghealth.presentation.viewmodel.profiledoctor.ProfileDoctorViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileDoctorFragment : Fragment() {

    private var _binding: FragmentProfileDoctorBinding? = null
    private val binding: FragmentProfileDoctorBinding
        get() = _binding!!

    private val viewModel by viewModels<ProfileDoctorViewModel>()

    private val patientsAdapter = PatientsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileDoctorBinding.inflate(inflater, container, false)

        binding.apply {
            patients.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = patientsAdapter.apply {
                    onItemClickListener = viewModel::onPatientClicked
                }
            }
        }

        viewModel.apply {
            start()
            userDataLiveData.observe(viewLifecycleOwner, ::setData)
            navigationLiveData.observe(viewLifecycleOwner, ::navigate)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setData(model: UserDataModel) {
        binding.profileCard.model = model
        patientsAdapter.items = model.patients ?: listOf()
    }

    private fun navigate(action: NavDirections) {
        findNavController().navigate(action)
    }
}