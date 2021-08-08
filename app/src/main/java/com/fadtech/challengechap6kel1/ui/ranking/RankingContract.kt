package com.fadtech.challengechap6kel1.ui.ranking

import com.fadtech.challengechap6kel1.base.BaseContract
import com.fadtech.challengechap6kel1.data.model.User

interface RankingContract {
    interface View : BaseContract.BaseView{
        //getting data from presenter
        fun getData()

        //callback get data
        fun onDataSuccess(users : List<User>)
        fun onDataFailed(msg : String?)
        fun onDataEmpty()

        // set loading state and message visibility
        fun setLoadingStatus(isLoading : Boolean)
        fun setEmptyStateVisibility(isDataEmpty : Boolean)

        //initialize list
        fun initList()

    }

//    interface Presenter : BaseContract.BasePresenter{
//        fun getUserRankingList()
//    }

    interface ViewModel{
        fun getUserRankingList()
    }
}