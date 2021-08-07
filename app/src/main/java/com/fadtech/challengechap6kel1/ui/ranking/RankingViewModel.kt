package com.fadtech.challengechap6kel1.ui.ranking

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fadtech.challengechap6kel1.base.Resource
import com.fadtech.challengechap6kel1.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RankingViewModel(private val repository: RankingRepository): ViewModel(),
    RankingContract.ViewModel{

    val rankingData = MutableLiveData<Resource<List<User>>>()

    override fun getUserRankingList() {
        rankingData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val todos = repository.getUserRanking()
                viewModelScope.launch(Dispatchers.Main) {
                    rankingData.value = Resource.Success(todos)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    //when getting data is error
                    rankingData.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }
}