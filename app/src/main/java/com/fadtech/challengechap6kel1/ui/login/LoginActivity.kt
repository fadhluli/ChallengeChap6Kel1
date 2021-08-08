package com.fadtech.challengechap6kel1.ui.login
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fadtech.challengechap6kel1.utils.StringUtils
import com.fadtech.challengechap6kel1.R
import com.fadtech.challengechap6kel1.base.GenericViewModelFactory
import com.fadtech.challengechap6kel1.base.Resource
import com.fadtech.challengechap6kel1.data.network.datasource.BinarDataSource
import com.fadtech.challengechap6kel1.data.network.entity.requests.authentication.LoginRequest
import com.fadtech.challengechap6kel1.data.network.services.BinarApiServices
import com.fadtech.challengechap6kel1.databinding.ActivityLoginBinding
import com.fadtech.challengechap6kel1.preference.SessionPreference
import com.fadtech.challengechap6kel1.preference.UserPreference
import com.fadtech.challengechap6kel1.ui.menu.MenuActivity
import com.fadtech.challengechap6kel1.ui.register.RegisterActivity


class LoginActivity : AppCompatActivity(), LoginContract.View {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var sessionPreference: SessionPreference
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)
        initView()
    }

    override fun setToolbar() {
        supportActionBar?.title = getString(R.string.text_title_toolbar_login)
    }

    override fun setOnClick() {
        binding.btnLogin.setOnClickListener {
            if (checkFormValidation()) {
                viewModel.loginUser(
                    LoginRequest(
                        email = binding.etEmail.text.toString(),
                        password = binding.etPassword.text.toString()
                    )
                )

            }
        }
        binding.llRegister.setOnClickListener {
            navigateToRegister()
        }
    }

    override fun navigateToHome() {
        val intent = Intent(this, MenuActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    override fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    override fun setLoadingState(isLoadingVisible: Boolean) {
        binding.pbLoading.visibility = if (isLoadingVisible) View.VISIBLE else View.GONE
    }

    override fun checkFormValidation(): Boolean {
        val email = binding.etEmail.text.toString()
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
        return isFormValid
    }

    override fun initView() {
        initViewModel()
        setToolbar()
        setOnClick()
    }

    override fun initViewModel() {
        sessionPreference = SessionPreference(this)
        userPreference = UserPreference(this)
        val apiService = BinarApiServices.getInstance(sessionPreference)
        apiService?.let {
            val dataSource = BinarDataSource(it)
            val repository = LoginRepository(dataSource)
            viewModel = GenericViewModelFactory(LoginViewModel(repository))
                .create(LoginViewModel::class.java)
        }
        viewModel.loginResponse.observe(this, { response ->
            when (response) {
                is Resource.Loading -> {
                    setLoadingState(true)
                }
                is Resource.Success -> {
                    setLoadingState(false)
                    Toast.makeText(this, R.string.text_login_success, Toast.LENGTH_SHORT).show()
                    response.data?.token?.let {
                        saveSessionLogin(it)
                    }
                    response.data?.username?.let {
                        saveUsername(it)
                    }
                    navigateToHome()
                }
                is Resource.Error -> {
                    setLoadingState(false)
                    Toast.makeText(this,"Your email and password wrong, please check again", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun saveSessionLogin(authToken : String) {
        sessionPreference.authToken = authToken
    }

    override fun saveUsername(username: String) {
        userPreference.userNamePlayerOne = username
    }

}