package com.ewake.walkinghealth.presentation.ui.fragment.profilepatient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ewake.walkinghealth.R
import com.ewake.walkinghealth.data.local.room.entity.UserActivityData
import com.ewake.walkinghealth.databinding.FragmentProfilePatientBinding
import com.ewake.walkinghealth.presentation.model.SimpleUserModel
import com.ewake.walkinghealth.presentation.model.UserDataModel
import com.ewake.walkinghealth.presentation.ui.fragment.profilepatient.adapter.ActivityDataAdapter
import com.ewake.walkinghealth.presentation.viewmodel.profilepatient.ProfilePatientViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
@AndroidEntryPoint
class ProfilePatientFragment : Fragment() {

    private var _binding: FragmentProfilePatientBinding? = null
    private val binding: FragmentProfilePatientBinding
        get() = _binding!!

    private val viewModel by viewModels<ProfilePatientViewModel>()

    private val activitiesAdapter = ActivityDataAdapter()

    private lateinit var datesAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args by navArgs<ProfilePatientFragmentArgs>()
        viewModel.login = args.userLogin

        datesAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, mutableListOf())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfilePatientBinding.inflate(inflater, container, false)

        viewModel.apply {
            userDataLiveData.observe(viewLifecycleOwner, ::setData)
            messageLiveData.observe(viewLifecycleOwner, ::showMessage)
            navigationLiveData.observe(viewLifecycleOwner, ::navigate)
            datesLiveData.observe(viewLifecycleOwner, ::setDateList)
            userActivityLiveData.observe(viewLifecycleOwner, ::setActivitiesData)
            start()
        }

        binding.apply {
            messages.setOnClickListener { viewModel.onMessagesClicked() }

            activities.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = activitiesAdapter
            }

            date.setAdapter(datesAdapter)
            date.setOnItemClickListener { _, _, position, _ ->
                viewModel.onDateChosen(datesAdapter.getItem(position)!!)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setData(data: UserDataModel) {
        binding.apply {
            profileCard.model = SimpleUserModel(data.login, data.fullname)
            data.doctor?.let { doctor.model = it }
        }
    }

    private fun showMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun navigate(action: NavDirections) {
        findNavController().navigate(action)
    }

    private fun setActivitiesData(list: List<UserActivityData>) {
        activitiesAdapter.items = list
    }

    private fun setDateList(list: List<String>) {
        datesAdapter.clear()
        datesAdapter.addAll(list.toMutableList())
    }
}