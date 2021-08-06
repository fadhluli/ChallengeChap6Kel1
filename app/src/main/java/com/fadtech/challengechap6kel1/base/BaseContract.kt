package com.fadtech.challengechap6kel1.base

interface BaseContract {
    interface BasePresenter {
        fun onDestroy()
    }
    interface ViewModel{

    }

    interface BaseView {
        fun initView()
        fun initViewModel()
    }
}