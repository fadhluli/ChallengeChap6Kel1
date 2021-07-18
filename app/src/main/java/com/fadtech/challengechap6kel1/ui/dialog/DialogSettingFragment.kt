package com.fadtech.challengechap6kel1.ui.dialog

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.fadtech.challengechap6kel1.R
import com.fadtech.challengechap6kel1.databinding.FragmentDialogSettingBinding
import com.fadtech.challengechap6kel1.ui.main.MainActivity
import com.fadtech.challengechap6kel1.ui.menu.MenuActivity
import com.fadtech.challengechap6kel1.ui.ranking.RankingListActivity
import com.google.android.material.snackbar.Snackbar


class DialogSettingFragment() : DialogFragment() {

    private lateinit var binding: FragmentDialogSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_dialog_round_white)

        binding.ivBtnCloseDialog.setOnClickListener {
            dialog?.dismiss()
        }

        binding.btnRankingDialogSetting.setOnClickListener {
//            context?.startActivity(Intent(context, RankingListActivity::class.java))
            (activity as MainActivity).navigateToRankingListActivity()
            dialog?.dismiss()
        }

        binding.btnReturnMenuDialogSetting.setOnClickListener {
            val intent = Intent(context, MenuActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

    }

    private fun finish() {
        context?.startActivity(Intent(context, MenuActivity::class.java))
    }


}