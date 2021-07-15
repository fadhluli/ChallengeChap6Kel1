package com.fadtech.challengechap6kel1.ui.inputform

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fadtech.challengechap6kel1.databinding.ActivityInputNamePlayerTwoBinding
import com.fadtech.challengechap6kel1.preference.UserPreference
import com.fadtech.challengechap6kel1.ui.menu.MenuActivity
import com.google.android.material.snackbar.Snackbar

class InputNamePlayerTwoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityInputNamePlayerTwoBinding
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputNamePlayerTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        setUpListener()
        prefilledCurrentName()

    }


    private fun prefilledCurrentName() {

            userPreference = UserPreference(this)
            //return binding.textField.setText(userPreference.userNamePlayerTwo.orEmpty())

    }

    private fun validate(): Boolean{

//        if (binding.textField.text.isNullOrEmpty()){
//            return false
//       }
         return true
    }

    private fun setUpListener(){
        binding.btnImgInputfieldPlayertwoGetstated.setOnClickListener{
            /*if (validate()){
                userPreference.userNamePlayerTwo = binding.textField.text.toString()
                startActivity(Intent(this, MenuActivity::class.java))

            }else{
                showMessage()
            }*/
        }
    }

    private fun showMessage(){
        Snackbar.make(binding.root,"Masukan Data Dengan Benar", Snackbar.LENGTH_SHORT).show()
    }









}