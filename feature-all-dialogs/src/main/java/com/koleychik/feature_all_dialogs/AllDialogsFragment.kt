package com.koleychik.feature_all_dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.koleychik.feature_all_dialogs.databinding.FragmentAllDialogsBinding

class AllDialogsFragment : Fragment() {

    private var _binding: FragmentAllDialogsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllDialogsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun subscribe(){

    }

    private fun showDialogs(){

    }

    private fun refresh(){

    }

    private fun loadingDialogs(){

    }

    private fun noneDialogs(){

    }

    private fun resetViews(){

    }

    private fun createOnClickListener(){

    }

    // TODO -> create ui for checkView, to get only favorites

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}