package com.fadtech.challengechap6kel1.ui.menu
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.fadtech.challengechap6kel1.databinding.ActivityInputNamePlayerTwoBinding
import com.fadtech.challengechap6kel1.preference.UserPreference
import com.fadtech.challengechap6kel1.ui.main.MainActivity

class InputNamePlayerTwoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputNamePlayerTwoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputNamePlayerTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefilledName()
        supportActionBar?.hide()
        setClickListener()
    }

    private fun prefilledName() {
        UserPreference(this).userNamePlayerTwo = binding.etPlayer2Name.text.toString()
    }

    private fun isFormFilled(): Boolean {
        val name = binding.etPlayer2Name.text.toString()
        var isFormValid = true

        if (name.isEmpty()) {
            isFormValid = false
            Toast
                .makeText(
                    this,
                    "NAME CAN NOT BE EMPTY",
                    Toast.LENGTH_SHORT
                )
                .show()
        }
        return isFormValid
    }

    private fun navigateToMainActivity() {
        if (isFormFilled()) {
            UserPreference(this).userNamePlayerTwo = binding.etPlayer2Name.text.toString()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun setClickListener() {
        binding.ivBtnNavigateToMenu.setOnClickListener {
            if (isFormFilled()) {
                navigateToMainActivity()
            }
        }
    }
}