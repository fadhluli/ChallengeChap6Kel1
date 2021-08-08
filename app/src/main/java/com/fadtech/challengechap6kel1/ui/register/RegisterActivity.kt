package com.fadtech.challengechap6kel1.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fadtech.challengechap6kel1.R
import com.fadtech.challengechap6kel1.base.GenericViewModelFactory
import com.fadtech.challengechap6kel1.base.Resource
import com.fadtech.challengechap6kel1.data.network.datasource.BinarDataSource
import com.fadtech.challengechap6kel1.data.network.entity.requests.authentication.RegisterRequest
import com.fadtech.challengechap6kel1.data.network.services.BinarApiServices
import com.fadtech.challengechap6kel1.databinding.ActivityRegisterBinding
import com.fadtech.challengechap6kel1.preference.SessionPreference
import com.fadtech.challengechap6kel1.ui.login.LoginActivity
import com.fadtech.challengechap6kel1.utils.StringUtils

class RegisterActivity : AppCompatActivity(), RegisterContract.View {
    private val TAG = RegisterActivity::class.java.simpleName
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private lateinit var sessionPreference: SessionPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        initView()
    }

    override fun setToolbar() {
        supportActionBar?.title = getString(R.string.text_title_toolbar_register)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setLoadingState(isLoadingVisible: Boolean) {
        binding.pbLoading.visibility = if (isLoadingVisible) View.VISIBLE else View.GONE
    }

    override fun checkFormValidation(): Boolean {
        val email = binding.etEmail.text.toString()
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()
        var isFormValid = true

        //for checking is email empty
        when {
            email.isEmpty() -> {
                isFormValid = false
                binding.tilEmail.isErrorEnabled = true
                binding.tilEmail.error = getString(R.string.error_email_empty)
            }
            StringUtils.isEmailValid(email).not() -> {
                isFormValid = false
                binding.tilEmail.isErrorEnabled = true
                binding.tilEmail.error = getString(R.string.error_email_invalid)
            }
            else -> {
                binding.tilEmail.isErrorEnabled = false
            }
        }
        //for checking is Password empty
        if (password.isEmpty()) {
            isFormValid = false
            binding.tilPassword.isErrorEnabled = true
            binding.tilPassword.error = getString(R.string.error_password_empty)
        } else {
            binding.tilPassword.isErrorEnabled = false
        }
        //for checking is Password empty
        if (username.isEmpty()) {
            isFormValid = false
            binding.tilUsername.isErrorEnabled = true
            binding.tilUsername.error = getString(R.string.error_username_empty)
        } else {
            binding.tilUsername.isErrorEnabled = false
        }
        return isFormValid
    }

    override fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    override fun initView() {
        initViewModel()
        setToolbar()
        setOnClick()
    }

    override fun initViewModel() {
        sessionPreference = SessionPreference(this)
        val apiService = BinarApiServices.getInstance(sessionPreference)
        apiService?.let {
            val dataSource = BinarDataSource(it)
            val repository = RegisterRepository(dataSource)
            viewModel = GenericViewModelFactory(RegisterViewModel(repository))
                .create(RegisterViewModel::class.java)
        }
        viewModel.registerResponse.observe(this, { response ->
            when (response) {
                is Resource.Loading -> {
                    setLoadingState(true)
                }
                is Resource.Success -> {
                    setLoadingState(false)
                    Toast.makeText(this, R.string.text_register_success, Toast.LENGTH_SHORT).show()
                    navigateToLogin()
                }
                is Resource.Error -> {
                    setLoadingState(false)
                    Toast.makeText(this,
                        getString(R.string.text_warning_email_or_username_registered),
                        Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun setOnClick() {
        binding.btnRegister.setOnClickListener {
            if (checkFormValidation()) {
                viewModel.registerUser(
                    RegisterRequest(
                        email = binding.etEmail.text.toString(),
                        password = binding.etPassword.text.toString(),
                        username = binding.etUsername.text.toString()
                    )
                )
            }
        }

        binding.ivReset.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}