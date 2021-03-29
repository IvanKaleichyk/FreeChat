package com.koleychik.feature_sign_up

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.koleychik.feature_sign_up.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        TODO("WHEN CLICK TO SING IN USING GOOGLE AUTH START DIALOG TO GET NAME")
    }

    private fun createOnCLickListener(){
        val onClickListener = View.OnClickListener{
            when(it.id){

            }
        }
    }

    private fun TextView.underlineText() {
        paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}