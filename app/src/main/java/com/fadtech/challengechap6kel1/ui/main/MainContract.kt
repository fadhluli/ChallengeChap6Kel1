package com.fadtech.challengechap6kel1.ui.main

import com.fadtech.challengechap6kel1.base.BaseContract
import com.fadtech.challengechap6kel1.data.model.User

interface MainContract {
    interface View: BaseContract.BaseView{
        fun onSuccess()
        fun onFailed()
    }
    interface Presenter: BaseContract.BasePresenter{
        fun insertUser(user: User)
    }
}