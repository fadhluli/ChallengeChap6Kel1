package com.fadtech.challengechap6kel1.ui.login


import com.fadtech.challengechap6kel1.base.BaseContract
import com.fadtech.challengechap6kel1.data.network.entity.requests.authentication.LoginRequest


interface LoginContract {
    interface ViewModel : BaseContract.ViewModel{
        fun loginUser(loginRequest: LoginRequest)
    }
    interface View : BaseContract.BaseView{
        fun setToolbar()
        fun setOnClick()
        fun navigateToHome()
        fun navigateToRegister()
        fun setLoadingState(isLoadingVisible: Boolean)
        fun checkFormValidation() : Boolean
        fun saveSessionLogin(authToken : String)
        fun saveUsername(username : String)
    }
}