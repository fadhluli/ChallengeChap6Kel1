package com.fadtech.challengechap6kel1.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fadtech.challengechap6kel1.base.Resource
import com.fadtech.challengechap6kel1.data.model.User
import com.fadtech.challengechap6kel1.data.network.entity.requests.HistoryRequest
import com.fadtech.challengechap6kel1.data.network.entity.responses.History.GetHistoryData
import com.fadtech.challengechap6kel1.data.network.entity.responses.History.PostHistoryData
import com.fadtech.challengechap6kel1.data.network.entity.responses.History.PostHistoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository): ViewModel(),
    MainContract.ViewModel{

    val transactionResult = MutableLiveData<Boolean>()
    val historyResponse = MutableLiveData<Resource<PostHistoryData>>()

    override fun insertUser(user: User) {
        viewModelScope.launch {
            try {
                val todoId = repository.insertUser(user)
                viewModelScope.launch (Dispatchers.Main){
                    transactionResult.value = todoId > 0
                }
            } catch (e: Exception) {
                viewModelScope.launch (Dispatchers.Main){
                    transactionResult.value = false
                }
            }
        }
    }

    override fun insertHistory(history: HistoryRequest) {
        historyResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.postHistory(history)
                viewModelScope.launch(Dispatchers.Main) {
                    //checking if response success
                    if (response.success) {
                        historyResponse.value = Resource.Success(response.postHistoryData)
                    } else {
//                        historyResponse.value = Resource.Error(response.errors)
                    }
                }
            } catch (e: Exception) {
                //set value to error message
                viewModelScope.launch(Dispatchers.Main) {
                    historyResponse.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }
}