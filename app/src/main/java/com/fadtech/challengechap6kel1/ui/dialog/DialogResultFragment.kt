package com.fadtech.challengechap6kel1.ui.dialog

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.fadtech.challengechap6kel1.R
import com.fadtech.challengechap6kel1.databinding.FragmentDialogResultBinding
import com.fadtech.challengechap6kel1.databinding.FragmentDialogSettingBinding
import com.fadtech.challengechap6kel1.ui.main.MainActivity
import com.fadtech.challengechap6kel1.ui.menu.MenuActivity

class DialogResultFragment(private val message: String) : DialogFragment() {


    private lateinit var binding: FragmentDialogResultBinding
    private lateinit var listener: DialogFragmentListener



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_dialog_round_white)


        binding.ivBtnCloseDialog.setOnClickListener {
            dialog?.dismiss()
            (activity as MainActivity ).showPlayerOne()
        }

        binding.tvTitleResult.text = "Result Game"

        binding.tvResultGame.text = message


        // Error can not runinng game play when reset game
        binding.btnPlayagainDialogResult.setOnClickListener {

            dismiss()
            if(this::listener.isInitialized){
                listener.onDialogDismiss()
            }

        }

        binding.btnReturnMenuDialogResult.setOnClickListener {
            finish()
        }

    }

    private fun finish() {
        context?.startActivity(Intent(context, MenuActivity::class.java))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is DialogFragmentListener){
            listener = context
        }
    }






}