package com.fadtech.challengechap6kel1.ui.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fadtech.challengechap6kel1.base.Resource
import com.fadtech.challengechap6kel1.data.network.entity.responses.History.GetHistoryData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: HistoryRepository) : ViewModel(),
    HistoryContract.ViewModel {

    val newsData = MutableLiveData<Resource<List<GetHistoryData>?>>()

    override fun getHistory() {
        newsData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getHistory()
                viewModelScope.launch(Dispatchers.Main) {
                    newsData.value = Resource.Success(response.data)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    newsData.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }

}