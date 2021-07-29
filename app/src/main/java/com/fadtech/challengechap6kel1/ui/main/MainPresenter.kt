package com.fadtech.challengechap6kel1.ui.main

import com.fadtech.challengechap6kel1.base.BasePresenterImpl
import com.fadtech.challengechap6kel1.data.local.room.datasource.UserDataSource
import com.fadtech.challengechap6kel1.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainPresenter(
    private val dataSource: UserDataSource,
    private val view: MainContract.View
) : BasePresenterImpl(), MainContract.Presenter {
    override fun insertUser(user: User) {
        scope.launch {
            try {
                val todoId = dataSource.insertUser(user)
                scope.launch(Dispatchers.Main) {
                    if (todoId > 0) {
                        view.onSuccess()
                    } else {
                        view.onFailed()
                    }
                }
            } catch (e: Exception) {
                scope.launch(Dispatchers.Main) {
                    view.onFailed()
                }
            }
        }
    }

}