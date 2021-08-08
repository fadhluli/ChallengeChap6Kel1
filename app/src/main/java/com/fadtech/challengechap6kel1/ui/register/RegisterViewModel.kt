package com.fadtech.challengechap6kel1.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fadtech.challengechap6kel1.base.Resource
import com.fadtech.challengechap6kel1.data.network.entity.requests.authentication.RegisterRequest
import com.fadtech.challengechap6kel1.data.network.entity.responses.authentication.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: RegisterRepository) : ViewModel(),
    RegisterContract.ViewModel {
    val registerResponse = MutableLiveData<Resource<UserResponse>>()

    override fun registerUser(registerRequest: RegisterRequest) {
        registerResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.postRegisterData(registerRequest)
                viewModelScope.launch(Dispatchers.Main) {
                    if (response.success) {
                        registerResponse.value = Resource.Success(response.data)
                    } else {
                        registerResponse.value = Resource.Error(response.errors)
                    }
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    registerResponse.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }
}