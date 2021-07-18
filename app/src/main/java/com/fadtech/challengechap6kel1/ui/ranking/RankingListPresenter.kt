package com.fadtech.challengechap6kel1.ui.ranking

import com.fadtech.challengechap6kel1.base.BasePresenterImpl
import com.fadtech.challengechap6kel1.data.local.room.datasource.UserDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RankingListPresenter(
    private val datasource: UserDataSource,
    private val view: RankingListContract.View
): BasePresenterImpl(), RankingListContract.Presenter {

    override fun getUserRankingList() {
        view.setLoadingStatus(true)
        scope.launch {
            try {
                val users = datasource.getUserRanking()
                scope.launch(Dispatchers.Main) {
                    //check if data is empty
                    if (!users.isNullOrEmpty()) {
                        //data is not empty
                        view.onDataSuccess(users)
                        view.setEmptyStateVisibility(false)
                    } else {
                        //data empty
                        view.onDataEmpty()
                        view.setEmptyStateVisibility(true)
                    }
                    view.setLoadingStatus(false)
                }
            } catch (e: Exception) {
                scope.launch(Dispatchers.Main) {
                    //when getting data is error
                    view.onDataFailed(e.message)
                    view.setLoadingStatus(false)
                    view.setEmptyStateVisibility(false)
                }
            }
        }
    }
}