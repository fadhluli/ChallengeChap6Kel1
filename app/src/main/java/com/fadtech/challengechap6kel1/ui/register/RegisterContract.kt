package com.fadtech.challengechap6kel1.ui.register

import com.fadtech.challengechap6kel1.base.BaseContract
import com.fadtech.challengechap6kel1.data.network.entity.request.authentification.RegisterRequest

interface RegisterContract {
    interface ViewModel : BaseContract.ViewModel {
        fun registerUser(registerRequest: RegisterRequest)
    }

    interface View : BaseContract.BaseView {
        fun setToolbar()
        fun setOnClick()
        fun setLoadingState(isLoadingVisible: Boolean)
        fun checkFormValidation() : Boolean
        fun navigateToLogin()
    }
}