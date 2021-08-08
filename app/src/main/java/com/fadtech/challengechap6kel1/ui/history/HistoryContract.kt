package com.fadtech.challengechap6kel1.ui.history

import com.fadtech.challengechap6kel1.base.BaseContract
import com.fadtech.challengechap6kel1.data.network.entity.responses.History.GetHistoryData

interface HistoryContract {
    interface View : BaseContract.BaseView{
        fun getData()
        fun onDataSuccess(data : List<GetHistoryData>)
        fun onDataFailed(msg : String?)
        fun onDataEmpty()
        fun setLoadingStatus(isLoading : Boolean)
        fun setEmptyStateVisibility(isDataEmpty : Boolean)
        fun initList()
    }

    interface ViewModel : BaseContract.ViewModel{
        fun getHistory()
    }
}