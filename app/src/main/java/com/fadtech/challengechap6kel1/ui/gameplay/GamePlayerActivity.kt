package com.fadtech.challengechap6kel1.ui.gameplay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fadtech.challengechap6kel1.ui.menu.MenuActivity
import com.fadtech.challengechap6kel1.R


class GamePlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_player)
    }

    private fun closeGame() {
        startActivity(Intent(this, MenuActivity::class.java))
    }

    override fun onBackPressed() {
        finish()
    }
}