package com.fadtech.challengechap6kel1.ui.dialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.fadtech.challengechap6kel1.R
import com.fadtech.challengechap6kel1.databinding.FragmentDialogSeetingMenuBinding
import com.fadtech.challengechap6kel1.preference.SessionPreference
import com.fadtech.challengechap6kel1.ui.login.LoginActivity
import com.fadtech.challengechap6kel1.ui.menu.MenuActivity
import com.fadtech.challengechap6kel1.ui.profile.ProfileActivity

class DialogSettingMenuFragment() : DialogFragment() {

    private lateinit var binding: FragmentDialogSeetingMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDialogSeetingMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_dialog_round_white)

        binding.ivBtnCloseDialog.setOnClickListener {
            dialog?.dismiss()
        }

        binding.btnProfileDialogSetting.setOnClickListener {
            val intent = Intent(context, ProfileActivity::class.java)
            startActivity(intent)
            dialog?.dismiss()
        }

        binding.btnLogoutDialogSetting.setOnClickListener {
            (activity as MenuActivity).deleteSession()
            context?.let {
                val intent = Intent(it, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }
    }
}