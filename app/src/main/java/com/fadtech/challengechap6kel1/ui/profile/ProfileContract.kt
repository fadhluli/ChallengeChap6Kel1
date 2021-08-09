package com.fadtech.challengechap6kel1.ui.profile

import com.fadtech.challengechap6kel1.base.BaseContract
import com.fadtech.challengechap6kel1.data.network.entity.responses.authentication.BaseAuthResponse
import com.fadtech.challengechap6kel1.data.network.entity.responses.authentication.UserResponse

interface ProfileContract {
    interface ViewModel : BaseContract.ViewModel{
        fun putUserData(username:String, email:String)
        fun getUserData()
    }

    interface View : BaseContract.BaseView{

        fun setOnClick()
        fun setLoadingState(isLoadingVisible: Boolean)
        fun checkFormValidation() : Boolean
        fun bindDataHeader(data : BaseAuthResponse<UserResponse, String>)
    }

}