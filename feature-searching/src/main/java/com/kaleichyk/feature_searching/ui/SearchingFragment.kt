package com.kaleichyk.feature_searching.ui

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import com.kaleichyk.feature_searching.R
import com.kaleichyk.feature_searching.databinding.FragmentSearchingBinding
import com.kaleichyk.feature_searching.di.SearchingFeatureComponentHolder
import com.kaleichyk.feature_searching.ui.adapter.SearchingAdapter
import com.kaleichyk.feature_searching.ui.viewModel.SearchingViewModel
import com.kaleichyk.feature_searching.ui.viewModel.ViewModelFactory
import com.kaleichyk.utils.navigation.NavigationSystem
import com.koleychik.models.states.DataState
import com.koleychik.models.users.User
import javax.inject.Inject

class SearchingFragment : Fragment() {

    private var _binding: FragmentSearchingBinding? = null
    private val binding get() = _binding!!

    @Inject
    internal lateinit var adapter: SearchingAdapter

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[SearchingViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        NavigationSystem.onStartFeature?.let { start -> start(this) }
        _binding = FragmentSearchingBinding.inflate(inflater, container, false)
        SearchingFeatureComponentHolder.getComponent().inject(this)
        createOnClickListener()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearching()
        setupRv()
        subscribe()
    }

    @Suppress("UNCHECKED_CAST")
    private fun subscribe() {
        viewModel.usersState.observe(viewLifecycleOwner) {
            resetViews()
            when (it) {
                is DataState.WaitingForStart -> getUsers(null)
                is DataState.Loading -> loading()
                is DataState.Result<*> -> handleData(it.body as List<User>)
                is DataState.Error -> error(it.message)
            }
        }
    }

    private fun handleData(list: List<User>) {
        if (list.isEmpty()) noneData()
        else setList(list)
    }

    private fun setList(list: List<User>) {
        adapter.list = list
        binding.rv.visibility = View.VISIBLE
    }

    private fun noneData() {
        binding.textInfo.run {
            setText(R.string.cannot_find_users)
            visibility = View.VISIBLE
        }
    }

    private fun error(message: String) {
        binding.textInfo.run {
            text = message
            visibility = View.VISIBLE
        }
    }

    private fun loading() {
        binding.shimmerFrameLayout.run {
            visibility = View.VISIBLE
            startShimmer()
        }
    }

    private fun getUsers(name: CharSequence?){
        if (name == null || name.isEmpty()) viewModel.get50LastUsers()
        else viewModel.searchUsersByName(name.toString())
    }

    private fun setupRv() {
        binding.rv.run {
            adapter = this@SearchingFragment.adapter
            itemAnimator = DefaultItemAnimator()
            overScrollMode = View.OVER_SCROLL_NEVER
        }
    }

    private fun createOnClickListener() {
        binding.clearIcon.setOnClickListener {
            binding.editText.run {
                text = null
                closeKeyboard(this)
            }
        }
    }

    private fun closeKeyboard(edt: EditText) {
        (requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            edt.windowToken,
            0
        )
    }

    private fun setupSearching() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                getUsers(binding.editText.text.toString().trim())
            }
        }
        binding.editText.addTextChangedListener(textWatcher)
    }

    private fun resetViews() {
        binding.shimmerFrameLayout.run {
            visibility = View.GONE
            stopShimmer()
        }

        with(binding) {
            textInfo.visibility = View.GONE
            rv.visibility = View.INVISIBLE
        }
    }

    override fun onStop() {
        super.onStop()
        SearchingFeatureComponentHolder.reset()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}