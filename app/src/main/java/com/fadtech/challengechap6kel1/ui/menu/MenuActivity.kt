package com.fadtech.challengechap6kel1.ui.menu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fadtech.challengechap6kel1.R
import com.fadtech.challengechap6kel1.data.constant.Constant
import com.fadtech.challengechap6kel1.databinding.ActivityMenuBinding
import com.fadtech.challengechap6kel1.preference.UserPreference
import com.fadtech.challengechap6kel1.ui.dialog.SettingDialogFragment
import com.fadtech.challengechap6kel1.ui.history.HistoryActivity
import com.fadtech.challengechap6kel1.ui.main.MainActivity
import com.fadtech.challengechap6kel1.ui.ranking.RankingActivity
import com.google.android.material.snackbar.Snackbar

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        supportActionBar!!.hide()
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTextMenuWelcome()
        showWelcomeMessage()
        setClickListenersPlayerPlayer()
    }

    private fun setTextMenuWelcome() {
        binding.tvTitleNamePlayerOne.text = String.format(
            getString(R.string.text_menu_welcome),
            UserPreference(this).userNamePlayerOne
        )
    }

    private fun showWelcomeMessage() {
        val snackBar = Snackbar.make(
            binding.root,
            getString(R.string.text_hello_snackbar) + UserPreference(this).userNamePlayerOne,
            Snackbar.LENGTH_SHORT
        )
        snackBar.setAction(getString(R.string.text_close_snackbar)) {
            snackBar.dismiss()
        }
        snackBar.show()
    }

    private fun setClickListenersPlayerPlayer() {
        binding.btnImgVsCom.setOnClickListener {
            val intent = Intent(this@MenuActivity, MainActivity::class.java).apply {
                putExtra(Constant.PLAY_MODE, 1)
            }
            startActivity(intent)
        }

        binding.btnImgVsPlayer.setOnClickListener {
            startActivity(Intent(this, InputNamePlayerTwoActivity::class.java))

        }
        binding.btnImgRanking.setOnClickListener {
            startActivity(Intent(this, RankingActivity::class.java))
        }

        binding.btnImgHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
        binding.ivSettingMenu.setOnClickListener {
            navigateToSetting()
        }
    }

    private fun navigateToSetting(){
        SettingDialogFragment().show(supportFragmentManager,null)
    }

}
