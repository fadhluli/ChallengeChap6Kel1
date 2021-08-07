package com.fadtech.challengechap6kel1.ui.login


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fadtech.challengechap6kel1.base.Resource
import com.fadtech.challengechap6kel1.data.network.entity.request.authentification.LoginRequest
import com.catnip.covidapp.data.network.entity.responses.authentification.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class LoginViewModel(private val repository: LoginRepository) : ViewModel(),
    LoginContract.ViewModel {
    val loginResponse = MutableLiveData<Resource<LoginResponse>>()

    override fun loginUser(loginRequest: LoginRequest) {
        //todo : set payload to loading
        loginResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.postLoginData(loginRequest)
                viewModelScope.launch(Dispatchers.Main) {
                    //checking if response success
                    if (response.success) {
                        loginResponse.value = Resource.Success(response.data)
                    } else {
                        loginResponse.value = Resource.Error(response.errors)
                    }
                }
            } catch (e: Exception) {
                //set value to error message
                viewModelScope.launch(Dispatchers.Main) {
                    loginResponse.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }

}