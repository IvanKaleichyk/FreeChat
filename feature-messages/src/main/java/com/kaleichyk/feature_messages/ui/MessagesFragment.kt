package com.kaleichyk.feature_messages.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaleichyk.feature_messages.MessagesAdapter
import com.kaleichyk.feature_messages.databinding.FragmentMessagesBinding
import com.kaleichyk.feature_messages.pagination.MessagesPaginationListener
import com.kaleichyk.utils.NavigationConstants.DIALOG
import com.koleychik.models.dialog.Dialog
import javax.inject.Inject

class MessagesFragment : Fragment() {

    private val adapter = MessagesAdapter()

    @Inject
    internal lateinit var paginationListenerFactory: MessagesPaginationListener.Factory

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: MessagesViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MessagesViewModel::class.java]
    }

    private val layoutManager by lazy {
        LinearLayoutManager(requireContext())
    }

    private val paginationListener by lazy {
        paginationListenerFactory.create(dialog.id, layoutManager, lifecycleScope)
    }

    private val dialog: Dialog by lazy {
        requireArguments().getParcelable(DIALOG) ?: Dialog()
    }

    private var _binding : FragmentMessagesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessagesBinding.inflate(inflater, container, false)
//        TODO("INIT FEATURE")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRV()
        createPaginationListener()
        subscribe()
    }


    private fun createPaginationListener(){
        if (viewModel.fullMessagesList == null) paginationListener.loadFirstData()
        else adapter.submitList(viewModel.fullMessagesList!!)
    }

    private fun setupRV(){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        TODO("DESTROY FEATURE")
    }

}