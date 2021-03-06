package com.fadtech.challengechap6kel1.ui.dialog

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.fadtech.challengechap6kel1.R
import com.fadtech.challengechap6kel1.databinding.FragmentDialogResultBinding
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
        dialog?.setCancelable(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_dialog_round_white)

        binding.ivBtnCloseDialog.setOnClickListener {
            dismiss()
            (activity as MainActivity).onDialogDismiss()
        }

        binding.tvTitleResult.text = getString(R.string.text_result_game)

        binding.tvResultGame.text = message


        // Error can not runinng game play when reset game
        binding.btnPlayagainDialogResult.setOnClickListener {
            dismiss()
            if (this::listener.isInitialized) {
                listener.onDialogDismiss()
            }
        }

        binding.btnReturnMenuDialogResult.setOnClickListener {
            finish()
            (activity as MainActivity).insertUserToDb()
        }
    }

    private fun finish() {
        val intent = Intent(context, MenuActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DialogFragmentListener) {
            listener = context
        }
    }
}