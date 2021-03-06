package com.fadtech.challengechap6kel1.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.fadtech.challengechap6kel1.R
import com.fadtech.challengechap6kel1.base.GenericViewModelFactory
import com.fadtech.challengechap6kel1.base.Resource
import com.fadtech.challengechap6kel1.data.network.datasource.BinarDataSource
import com.fadtech.challengechap6kel1.data.network.services.BinarApiServices
import com.fadtech.challengechap6kel1.preference.SessionPreference
import com.fadtech.challengechap6kel1.ui.landingpage.LandingPageActivity
import com.fadtech.challengechap6kel1.ui.login.LoginActivity
import com.fadtech.challengechap6kel1.ui.menu.MenuActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity(), SplashScreenContract.View {
    private val TAG = SplashScreenActivity::class.java.simpleName
    private lateinit var viewModel: SplashScreenViewModel
    private lateinit var sessionPreference: SessionPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()
        initView()
    }
    
    private fun setEventSplash() {
        val intent = Intent(this@SplashScreenActivity, LandingPageActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    override fun navigateToMenu() {
        val intent = Intent(this, MenuActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    override fun checkLogin() {
        if (sessionPreference.authToken != null) {
            viewModel.getSyncData()
        } else {
            //navigate to login, if no token
            lifecycleScope.launch(Dispatchers.IO) {
                delay(1000)
                lifecycleScope.launch(Dispatchers.Main) {
                    setEventSplash()
                }
            }
        }
    }

    override fun deleteSessionLogin() {
        sessionPreference.deleteSession()
    }

    override fun initView() {
        initViewModel()
        checkLogin()
    }

    override fun initViewModel() {
        sessionPreference = SessionPreference(this)
        val apiService = BinarApiServices.getInstance(sessionPreference)
        apiService?.let {
            val dataSource = BinarDataSource(it)
            val repository = SplashScreenRepository(dataSource)
            viewModel = GenericViewModelFactory(SplashScreenViewModel(repository))
                .create(SplashScreenViewModel::class.java)
        }
        viewModel.syncData.observe(this, { response ->
            when (response) {
                is Resource.Loading -> {
                    Log.d(TAG, "initViewModel:Splash Loading")
                }
                is Resource.Success -> {
                    navigateToMenu()
                }
                is Resource.Error -> {
                    Toast.makeText(this, "Session Expired", Toast.LENGTH_SHORT).show()
                    deleteSessionLogin()
                    setEventSplash()
                }
            }
        })

    }
}
