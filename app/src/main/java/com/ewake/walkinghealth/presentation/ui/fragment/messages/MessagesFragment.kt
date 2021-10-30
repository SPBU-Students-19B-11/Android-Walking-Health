package com.ewake.walkinghealth.presentation.ui.fragment.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ewake.walkinghealth.databinding.FragmentMessagesBinding
import com.ewake.walkinghealth.presentation.model.MessageModel
import com.ewake.walkinghealth.presentation.ui.activity.MainActivity
import com.ewake.walkinghealth.presentation.ui.fragment.messages.adapter.MessagesAdapter
import com.ewake.walkinghealth.presentation.viewmodel.messages.MessagesViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
@AndroidEntryPoint
class MessagesFragment : Fragment() {

    private var _binding: FragmentMessagesBinding? = null
    private val binding: FragmentMessagesBinding
        get() = _binding!!

    private val viewModel by viewModels<MessagesViewModel>()

    private val messagesAdapter = MessagesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessagesBinding.inflate(inflater, container, false)

        binding.apply {
            messages.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
                adapter = messagesAdapter
            }
        }

        (activity as MainActivity).isBottomNavigationVisible = true

        viewModel.apply {
            messagesLiveData.observe(viewLifecycleOwner, ::setItems)
            messageLiveData.observe(viewLifecycleOwner, ::showMessage)
            start()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setItems(items: List<MessageModel>) {
        messagesAdapter.items = items.toMutableList()
    }

    private fun showMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}