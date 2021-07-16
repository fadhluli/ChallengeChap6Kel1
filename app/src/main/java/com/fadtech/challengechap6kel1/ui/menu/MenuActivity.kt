package com.fadtech.challengechap6kel1.ui.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fadtech.challengechap6kel1.R
import com.fadtech.challengechap6kel1.data.preference.UserPreference
import com.fadtech.challengechap6kel1.databinding.ActivityMenuBinding
import com.fadtech.challengechap6kel1.ui.gameplay.GameComActivity
import com.fadtech.challengechap6kel1.ui.gameplay.GameRankingActivity
import com.google.android.material.snackbar.Snackbar

class MenuActivity : AppCompatActivity() {

        private lateinit var binding: ActivityMenuBinding
        private var selectedButton: Int =1


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_menu)
            supportActionBar!!.hide()
            binding =ActivityMenuBinding.inflate(layoutInflater)
            setContentView(binding.root)
            setTitlePage()
            showWelcomeMessage()
            setClickListenersPlayerPlayer()
        }

        private fun setTitlePage() {
            binding.tvTitleMenu.text = String.format(
                getString(R.string.text_title_menu),
                UserPreference(this).userName
            )
        }
        private fun showWelcomeMessage() {
            val snackBar = Snackbar.make(
                binding.root,
                getString(R.string.text_hello_snackbar) + UserPreference(this).userName,
                Snackbar.LENGTH_INDEFINITE
            )
            snackBar.setAction(getString(R.string.text_close_snackbar)) {
                snackBar.dismiss()
            }
            snackBar.show()
        }

        private fun setClickListenersPlayerPlayer() {
            binding.flMenuPlayerVsCom.setOnClickListener {
                selectedButton = 1
                binding.flMenuPlayerVsCom.setBackgroundColor(R.drawable.bg_slider3_uilanding)
                binding.flMenuPlayerVsPlayer.setBackgroundColor(0)
                startActivity(Intent(this, GameComActivity::class.java))
            }

            binding.flMenuPlayerVsPlayer.setOnClickListener {
                selectedButton = 2
                binding.flMenuPlayerVsPlayer.setBackgroundColor(R.drawable.bg_slider3_uilanding)
                binding.flMenuPlayerVsCom.setBackgroundColor(0)
                startActivity(Intent(this, InputNamePlayerTwoActivity::class.java))

            }
            binding.flMenuRanking.setOnClickListener {
                selectedButton = 3
                binding.flMenuPlayerVsPlayer.setBackgroundColor(R.drawable.bg_slider3_uilanding)
                binding.flMenuPlayerVsCom.setBackgroundColor(0)
                startActivity(Intent(this, GameRankingActivity::class.java))
            }
        }
import com.fadtech.challengechap6kel1.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()


    }




}
        override fun onBackPressed() {
            finish()
        }
    }

