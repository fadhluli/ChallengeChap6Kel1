package com.fadtech.challengechap6kel1.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fadtech.challengechap6kel1.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository): ViewModel(),
    MainContract.ViewModel{

    val transactionResult = MutableLiveData<Boolean>()

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
}