package com.fadtech.challengechap6kel1.ui.landingpage.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fadtech.challengechap6kel1.databinding.FragmentLandingPage1Binding

class LandingPage1Fragment : Fragment() {
    private lateinit var binding: FragmentLandingPage1Binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLandingPage1Binding.inflate(inflater, container, false)
        return binding.root
    }

}