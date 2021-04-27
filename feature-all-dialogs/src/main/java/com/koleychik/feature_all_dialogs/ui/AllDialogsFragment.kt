package com.koleychik.feature_all_dialogs.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import coil.load
import com.kaleichyk.data.CurrentUser
import com.koleychik.feature_all_dialogs.AllDialogFeatureNavigationApi
import com.koleychik.feature_all_dialogs.R
import com.koleychik.feature_all_dialogs.databinding.FragmentAllDialogsBinding
import com.koleychik.feature_all_dialogs.di.AllDialogsFeatureComponentHolder
import com.koleychik.feature_all_dialogs.ui.adapter.AllDialogsAdapter
import com.koleychik.feature_all_dialogs.ui.viewModels.AllDialogsViewModel
import com.koleychik.feature_all_dialogs.ui.viewModels.ViewModelFactory
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.models.Dialog
import com.koleychik.models.results.DialogsResult
import com.koleychik.module_injector.NavigationSystem
import javax.inject.Inject

class AllDialogsFragment : Fragment() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    @Inject
    internal lateinit var loadingApi: LoadingApi

    @Inject
    internal lateinit var adapter: AllDialogsAdapter

    @Inject
    internal lateinit var navigationApi: AllDialogFeatureNavigationApi

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AllDialogsViewModel::class.java]
    }

    private var _binding: FragmentAllDialogsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        NavigationSystem.onStartFeature?.let { start -> start(this) }
        _binding = FragmentAllDialogsBinding.inflate(inflater, container, false)
        AllDialogsFeatureComponentHolder.getComponent().inject(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoading()
        setupRV()
        createOnClickListener()
        createOnSwipeListener()
        loadUserIcon()
        subscribe()
    }

    private fun subscribe() {
        viewModel.serverResponse.observe(viewLifecycleOwner) {
            resetViews()
            when (it) {
                null -> {
                    adapter.clearList()
                    loadingDialogs(
                        binding.switchFavorites.isChecked,
                        adapter.start,
                        (adapter.start + adapter.period).toLong()
                    )
                }
                is DialogsResult.Successful -> mapList(it.list)
                is DialogsResult.ServerError -> error(it.message)
                is DialogsResult.DataError -> error(requireContext().getString(it.message))
            }
        }
    }

    private fun mapList(list: List<Dialog>) {
        if (list.isEmpty()) noneDialogs()
        else addDialogs(list)
    }

    private fun addDialogs(list: List<Dialog>) {
        binding.rv.visibility = View.VISIBLE
        adapter.list = list
    }

    private fun loading() {
        loadingApi.isVisible = true
        with(binding) {
            rv.visibility = View.INVISIBLE
            textInfo.visibility = View.GONE
        }
    }

    private fun loadingDialogs(isOnlyFavorites: Boolean, startAt: Int, end: Long) {
        loading()
        if (isOnlyFavorites) viewModel.getFavoritesDialogs()
        else viewModel.getDialogs(startAt, end)
    }

    private fun error(message: String) {
        binding.textInfo.text = message
    }

    private fun noneDialogs() {
        binding.textInfo.run {
            setText(R.string.cannotFindDialogs)
            visibility = View.VISIBLE
        }
    }

    private fun resetViews() {
        loadingApi.isVisible = false
        with(binding) {
            rv.visibility = View.INVISIBLE
            textInfo.visibility = View.GONE
        }
    }

    private fun createOnClickListener() {
        val onClickListener = View.OnClickListener {
            when (it.id) {
                binding.searchIcon.id -> navigationApi.fromDialogsFeatureGoToSearchingFeature()
                binding.userIcon.id -> navigationApi.fromDialogsFeatureGoToUserInfoFeature()
            }
        }
        with(binding) {
            searchIcon.setOnClickListener(onClickListener)
            userIcon.setOnClickListener(onClickListener)
        }
    }

    private fun createOnSwipeListener() {
        binding.switchFavorites.setOnCheckedChangeListener { _, _ ->
            adapter.list = mutableListOf()
            viewModel.serverResponse.value = null
        }
    }

    private fun setupRV() {
        with(binding.rv) {
            itemAnimator = DefaultItemAnimator()
            adapter = this@AllDialogsFragment.adapter
        }
        adapter.onLoadNewList = { start, end ->
            loadingDialogs(binding.switchFavorites.isChecked, start, end)
        }
    }

    private fun setupLoading() {
        loadingApi.create(requireView())
        with(binding.viewStubLoading) {
            layoutResource = loadingApi.layoutRes
            inflate()
        }
    }

    private fun loadUserIcon() {
        CurrentUser.user?.icon?.let { uri ->
            binding.userIcon.load(uri)
        }
    }

    override fun onStop() {
        super.onStop()
        AllDialogsFeatureComponentHolder.reset()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}