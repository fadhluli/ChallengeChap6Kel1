package com.fadtech.challengechap6kel1.ui.landingpage.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fadtech.challengechap6kel1.data.preference.UserPreference
import com.fadtech.challengechap6kel1.databinding.ActivityLandingPageBinding
import com.fadtech.challengechap6kel1.databinding.FragmentLandingPage3Binding

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
            binding.etPlayer1Name.setText(userPreference.userNamePlayer1.orEmpty())
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
            userPreference.userNamePlayer1 = binding.etPlayer1Name.text.toString()
            Toast.makeText(context, "NAVIGATE TO GAME MENU", Toast.LENGTH_SHORT).show()
            //context?.startActivity(Intent(context, MenuActivity::class.java))
        }
    }

    private fun setClickListener(){
            binding.ivBtnNavigateToMenu.setOnClickListener {
                if(isFormFilled()){
                navigateToGameMenu()
                }
            }
        }
}