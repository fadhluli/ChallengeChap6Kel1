package com.fadtech.challengechap6kel1.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fadtech.challengechap6kel1.base.Resource
import com.fadtech.challengechap6kel1.data.network.entity.responses.authentication.BaseAuthResponse
import com.fadtech.challengechap6kel1.data.network.entity.responses.authentication.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: ProfileRepository) : ViewModel(),
    ProfileContract.ViewModel {
    val putUserDataResponse = MutableLiveData<Resource<UserResponse>>()
    val getUserDataResponse = MutableLiveData<Resource<BaseAuthResponse<UserResponse, String>>>()

    override fun putUserData(username:String, email:String) {
        putUserDataResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.putUserData(username,email)
                viewModelScope.launch(Dispatchers.Main) {
                    //checking if response success
                    if (response.success) {
                        putUserDataResponse.value = Resource.Success(response.data)
                    } else {
                        putUserDataResponse.value = Resource.Error(response.errors)
                    }
                }

            }catch (e: Exception){
                viewModelScope.launch(Dispatchers.Main) {
                    putUserDataResponse.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }

    override fun getUserData() {
        getUserDataResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getUserData()
                viewModelScope.launch(Dispatchers.Main) {
                    getUserDataResponse.value = Resource.Success(response)
                }
            }catch (e : Exception){
                viewModelScope.launch(Dispatchers.Main) {
                    getUserDataResponse.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }


}