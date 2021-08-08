package com.fadtech.challengechap6kel1.ui.landingpage.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.fadtech.challengechap6kel1.databinding.FragmentLandingPage3Binding
import com.fadtech.challengechap6kel1.preference.UserPreference
import com.fadtech.challengechap6kel1.ui.menu.MenuActivity

class LandingPage3Fragment : Fragment() {

    private lateinit var binding: FragmentLandingPage3Binding
    private lateinit var userPreference: UserPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLandingPage3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefilledName()
        setClickListener()
    }

    private fun prefilledName() {
        context?.let {
            userPreference = UserPreference(it)
            binding.etPlayer1Name.setText(userPreference.userNamePlayerOne.orEmpty())
        }
    }

    private fun isFormFilled(): Boolean {
        val name = binding.etPlayer1Name.text.toString()
        var isFormValid = true

        if (name.isEmpty()) {
            isFormValid = false
            Toast
                .makeText(
                    context,
                    "NAME CAN NOT BE EMPTY",
                    Toast.LENGTH_SHORT
                )
                .show()
        }
        return isFormValid
    }

    private fun navigateToGameMenu() {
        if (isFormFilled()) {
            userPreference.userNamePlayerOne = binding.etPlayer1Name.text.toString()

            val intent = Intent(context, MenuActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun setClickListener() {
        binding.ivBtnNavigateToMenu.setOnClickListener {
            if (isFormFilled()) {
                navigateToGameMenu()
            }
        }
    }
}