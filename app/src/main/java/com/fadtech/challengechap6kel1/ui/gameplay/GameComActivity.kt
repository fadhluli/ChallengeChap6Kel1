package com.fadtech.challengechap6kel1.ui.gameplay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fadtech.challengechap6kel1.MainActivity
import com.fadtech.challengechap6kel1.R

class GameComActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_com)
    }

    private fun closeGame() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onBackPressed() {
        finish()
    }
}
