package com.fadtech.challengechap6kel1.base

interface BaseContract {
    interface ViewModel {
//        fun onDestroy()
    }

    interface BaseView {
        fun initView()
        fun initViewModel()
    }
}