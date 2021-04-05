package com.koleychik.feature_password_utils.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.koleychik.feature_password_utils.databinding.FragmentPasswordRecoveryBinding

class PasswordRecoveryFragment : Fragment() {

    private var _binding: FragmentPasswordRecoveryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordRecoveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}