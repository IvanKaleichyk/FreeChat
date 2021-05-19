package com.koleychik.feature_all_dialogs.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import coil.load
import com.kaleichyk.utils.CurrentUser
import com.kaleichyk.utils.NavigationConstants
import com.koleychik.feature_all_dialogs.AllDialogFeatureNavigationApi
import com.koleychik.feature_all_dialogs.R
import com.koleychik.feature_all_dialogs.databinding.FragmentAllDialogsBinding
import com.koleychik.feature_all_dialogs.di.AllDialogsFeatureComponentHolder
import com.koleychik.feature_all_dialogs.ui.adapter.AllDialogsAdapter
import com.koleychik.feature_all_dialogs.ui.viewModels.AllDialogsViewModel
import com.koleychik.feature_all_dialogs.ui.viewModels.ViewModelFactory
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.models.Dialog
import com.koleychik.models.states.DataState
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

    @Suppress("UNCHECKED_CAST")
    private fun subscribe() {
        viewModel.dialogState.observe(viewLifecycleOwner) {
            resetViews()
            when (it) {
                is DataState.WaitingForStart -> loadingDialogs(
                    binding.switchFavorites.isChecked,
                    adapter.start,
                    (adapter.start + adapter.period).toLong()
                )
                is DataState.Loading -> loading()
                is DataState.Error -> error(it.message)
                is DataState.Result<*> -> handleList(it.body as List<Dialog>)
            }
        }
    }

    private fun handleList(list: List<Dialog>) {
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
        adapter.clearList()
        if (isOnlyFavorites) viewModel.getFavoritesDialogs(CurrentUser.user!!.listDialogsId)
        else viewModel.getDialogs(CurrentUser.user!!.listDialogsId, startAt, end)
    }

    private fun error(message: String) {
        binding.textInfo.text = message
        binding.textInfo.visibility = View.VISIBLE
    }

    private fun noneDialogs() {
        binding.textInfo.setText(R.string.cannotFindDialogs)
        binding.textInfo.visibility = View.VISIBLE
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
                binding.userIcon.id -> navigationApi.fromDialogsFeatureGoToUserInfoFeature(Bundle().apply {
                    putParcelable(NavigationConstants.USER, CurrentUser.user!!)
                })
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
            viewModel.resetListDialogs()
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
        loadingApi.isVisible = false
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