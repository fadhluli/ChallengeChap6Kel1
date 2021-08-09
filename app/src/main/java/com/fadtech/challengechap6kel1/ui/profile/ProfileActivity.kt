package com.fadtech.challengechap6kel1.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fadtech.challengechap6kel1.R
import com.fadtech.challengechap6kel1.base.GenericViewModelFactory
import com.fadtech.challengechap6kel1.base.Resource
import com.fadtech.challengechap6kel1.data.network.datasource.ProfileDataSource
import com.fadtech.challengechap6kel1.data.network.entity.responses.authentication.BaseAuthResponse
import com.fadtech.challengechap6kel1.data.network.entity.responses.authentication.UserResponse
import com.fadtech.challengechap6kel1.data.network.services.ProfileApiServices
import com.fadtech.challengechap6kel1.databinding.ActivityProfileBinding
import com.fadtech.challengechap6kel1.preference.SessionPreference
import com.fadtech.challengechap6kel1.utils.StringUtils

class ProfileActivity : AppCompatActivity(), ProfileContract.View {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private lateinit var sessionPreference: SessionPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        initView()
    }

    override fun setOnClick() {
        binding.btnUpdateProfile.setOnClickListener {
            if (checkFormValidation()) {
                viewModel.putUserData(
                    username = binding.etProfileUsername.text.toString(),
                    email = binding.etProfileEmail.text.toString()
                )
            }
        }
        binding.ivIcBack.setOnClickListener {
//            val intent = Intent(this, MenuActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//            startActivity(intent)
//            finish()
            onBackPressed()
        }
    }



    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun setLoadingState(isLoadingVisible: Boolean) {
        binding.pbLoading.visibility = if (isLoadingVisible) View.VISIBLE else View.GONE
    }

    override fun checkFormValidation(): Boolean {
        val email = binding.etProfileEmail.text.toString()
        val username = binding.etProfileUsername.text.toString()
        var isFormValid = true

        when {
            email.isEmpty() -> {
                isFormValid = false
                binding.tilProfileEmail.isErrorEnabled = true
                binding.tilProfileEmail.error = getString(R.string.error_email_empty)
            }
            StringUtils.isEmailValid(email).not() -> {
                isFormValid = false
                binding.tilProfileEmail.isErrorEnabled = true
                binding.tilProfileEmail.error = getString(R.string.error_email_invalid)
            }
            else -> {
                binding.tilProfileEmail.isErrorEnabled = false
            }
        }
        if (username.isEmpty()) {
            isFormValid = false
            binding.tilProfileUsername.isErrorEnabled = true
            binding.tilProfileUsername.error = getString(R.string.error_username_empty)
        } else {
            binding.tilProfileUsername.isErrorEnabled = false
        }
        return isFormValid
    }

    override fun bindDataHeader(data: BaseAuthResponse<UserResponse, String>) {
        binding.etProfileUsername.setText(data.data.username)
        binding.etProfileEmail.setText(data.data.email)
    }

    override fun initView() {
        initViewModel()
        setOnClick()
    }

    override fun initViewModel() {
        sessionPreference = SessionPreference(this)
        val apiServices = ProfileApiServices.getInstance(sessionPreference)
        apiServices?.let {
            val dataSource = ProfileDataSource(it)
            val repository = ProfileRepository(dataSource)
            viewModel = GenericViewModelFactory(ProfileViewModel(repository))
                .create(ProfileViewModel::class.java)
        }
        viewModel.getUserDataResponse.observe(this, {
            when (it) {
                is Resource.Loading -> {
                    setLoadingState(true)
                }
                is Resource.Success -> {
                    setLoadingState(false)
                    it.data?.let { data ->
                        bindDataHeader(data)
                    }
                }
                is Resource.Error -> {
                    setLoadingState(false)

                }
            }
        })
        viewModel.getUserData()
        viewModel.putUserDataResponse.observe(this, { response ->
            when (response) {
                is Resource.Loading -> {
                    setLoadingState(true)
                }
                is Resource.Success -> {
                    setLoadingState(false)
                    Toast.makeText(this, R.string.text_update_success, Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    setLoadingState(false)
                    Toast.makeText(this, R.string.text_update_error, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}