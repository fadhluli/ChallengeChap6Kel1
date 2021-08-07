package com.fadtech.challengechap6kel1.ui.splashscreen

import com.fadtech.challengechap6kel1.base.BaseContract

interface SplashScreenContract {
    interface ViewModel : BaseContract.ViewModel{
        fun getSyncData()
    }
    interface View : BaseContract.BaseView{
        fun navigateToLogin()
        fun navigateToHome()
        fun checkLogin()
        fun deleteSessionLogin()
    }
}