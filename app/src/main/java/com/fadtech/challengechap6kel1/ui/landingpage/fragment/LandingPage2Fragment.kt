package com.fadtech.challengechap6kel1.ui.landingpage.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fadtech.challengechap6kel1.databinding.FragmentLandingPage2Binding

class LandingPage2Fragment : Fragment() {
    private lateinit var binding: FragmentLandingPage2Binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLandingPage2Binding.inflate(inflater, container, false)
        return binding.root
    }

}