package com.fadtech.challengechap6kel1.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fadtech.challengechap6kel1.R
import com.fadtech.challengechap6kel1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var playMode: Int? = null
    private var player2Choice: Int? = null
    private var player1Choice: Int? = null
    private var flag: Int = -1
    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }
}