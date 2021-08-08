package com.fadtech.challengechap6kel1.ui.dialog

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.fadtech.challengechap6kel1.R
import com.fadtech.challengechap6kel1.databinding.FragmentSettingDialogBinding
import com.fadtech.challengechap6kel1.preference.SessionPreference
import com.fadtech.challengechap6kel1.ui.login.LoginActivity
import com.fadtech.challengechap6kel1.ui.menu.MenuActivity
import com.fadtech.challengechap6kel1.ui.profile.ProfileActivity

class SettingDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentSettingDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_dialog_round_white)
        binding.btnProfile.setOnClickListener {
            context?.startActivity(Intent(context, ProfileActivity::class.java))
        }
        binding.btnLogout.setOnClickListener {
            logout()

        }
        binding.btnExit.setOnClickListener {
            context?.startActivity(Intent(context, MenuActivity::class.java))
            Toast.makeText(context, "Return To Menu", Toast.LENGTH_SHORT).show()
        }
    }



    private fun logout(){
        deleteSession()
        navigateToLogin()
    }

    private fun navigateToLogin(){
        context?.startActivity(Intent(context, LoginActivity::class.java))
        Toast.makeText(context, "Logout Success!", Toast.LENGTH_SHORT).show()
    }

    private fun deleteSession(){
        (activity as SessionPreference).deleteSession()
    }
}