package com.ewake.walkinghealth.presentation.ui.fragment.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.ewake.walkinghealth.R
import com.ewake.walkinghealth.databinding.FragmentRegisterBinding
import com.ewake.walkinghealth.presentation.model.RegistrationModel
import com.ewake.walkinghealth.presentation.model.SimpleUserModel
import com.ewake.walkinghealth.presentation.ui.activity.MainActivity
import com.ewake.walkinghealth.presentation.viewmodel.register.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding: FragmentRegisterBinding
        get() = _binding!!

    private val viewModel by viewModels<RegisterViewModel>()

    private lateinit var doctorsAdapter: ArrayAdapter<String>

    private val screenData: RegistrationModel
        get() = RegistrationModel(
            login = binding.login.text.toString(),
            password = binding.password.text.toString(),
            fullname = binding.fullname.text.toString(),
            isDoctor = binding.isDoctor.isChecked,
            selectedDoctor = if (!binding.isDoctor.isChecked && !binding.doctors.text.isNullOrEmpty()) {
                SimpleUserModel(fullname = binding.doctors.text.toString())
            } else {
                null
            },
            stepLength = binding.step.text?.toString()?.toIntOrNull()?.times(0.43)?.div(100)
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doctorsAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, mutableListOf())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.apply {
            isDoctor.setOnCheckedChangeListener { _, isChecked ->
                doctorsContainer.isVisible = !isChecked
                stepContainer.isVisible = !isChecked
            }

            register.setOnClickListener {
                if (validate())
                    viewModel.onRegisterButtonClicked(screenData)
            }

            doctors.setAdapter(doctorsAdapter)
        }

        viewModel.apply {
            messageLiveData.observe(viewLifecycleOwner, ::showMessage)
            navigationLiveData.observe(viewLifecycleOwner, ::navigate)
            doctorsLiveData.observe(viewLifecycleOwner, ::setDoctors)
            start()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validate(): Boolean {
        var result = true

        binding.apply {
            loginContainer.error = if (login.text.isNullOrEmpty()) {
                result = false
                "?????????????? ??????????"
            } else {
                null
            }

            passwordContainer.error = if (password.text.isNullOrEmpty()) {
                result = false
                "?????????????? ????????????"
            } else {
                null
            }

            repassword.error = when {
                repassword.text.isNullOrEmpty() -> {
                    result = false
                    "?????????????????? ????????????"
                }
                repassword.text.toString() != password.text.toString() -> {
                    result = false
                    "???????????? ???? ??????????????????"
                }
                else -> {
                    null
                }
            }

            fullnameContainer.error = if (fullname.text.isNullOrEmpty()) {
                result = false
                "?????????????? ??????"
            } else {
                null
            }

            doctorsContainer.error = if (!isDoctor.isChecked && doctors.text.isNullOrEmpty()) {
                result = false
                "???????????????? ???????????????? ??????????"
            } else {
                null
            }
        }

        return result
    }

    private fun showMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun navigate(action: NavDirections) {
        findNavController().navigate(action)
    }

    private fun setDoctors(list: MutableList<String>) {
        doctorsAdapter.clear()
        doctorsAdapter.addAll(list)
    }
}