package com.fadtech.challengechap6kel1.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isInvisible
import com.fadtech.challengechap6kel1.R
import com.fadtech.challengechap6kel1.data.constant.Constant
import com.fadtech.challengechap6kel1.databinding.ActivityMainBinding
import com.fadtech.challengechap6kel1.enum.GameMechanic
import com.fadtech.challengechap6kel1.preference.UserPreference
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isGameFinished: Boolean = false
    private var playMode: Int? = null
    private var player2: Int? = null
    private var player1: Int? = null
    private var flag: Int = -1
    private val TAG = MainActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        start()
        onResetClick()
        onSettingClick()
    }


    private fun start() {
        playMode = intent.getIntExtra(Constant.PLAY_MODE, 0)
        binding.tvNamePlayerOne.text = UserPreference(this).userName

        if (playMode == 0) {
            flag = 0
            binding.tvNameCpu.text = getString(R.string.text_name_enemy_player)
            onPlayerOneClick()
            onPlayerTwoClick()
        } else {
            flag = 1
            binding.tvNameCpu.text = getString(R.string.text_name_enemy_comp)
            onPlayerOneClick()
        }
    }

    private fun onResetClick() {
        binding.ivReset.setOnClickListener {
            resetGame()
            isGameFinished = false
        }
    }

    private fun onSettingClick() {
        binding.ivSetting.setOnClickListener {
            val intent = Intent(this@MainActivity, this::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun onPlayerOneClick() {
        var random: Int
        binding.flActionPlayerRock.setOnClickListener {
           if ( !isGameFinished) {
               player1 = 0
               setSelectPlayer(0)
               showToastFromPlayer1Choice(player1!!)
               if (playMode != 0) {
                   random = Random.nextInt(0, 3)
                   gamePlay(player1!!, random)
               }
           }
           }
        binding.flActionPlayerPapper.setOnClickListener {
            if (!isGameFinished) {
                player1 = 1
                setSelectPlayer(1)
                showToastFromPlayer1Choice(player1!!)
                if (playMode != 0) {
                    random = Random.nextInt(0, 3)
                    gamePlay(player1!!, random)
                }
            }
        }
        binding.flActionPlayerScissor.setOnClickListener {
            if (!isGameFinished) {
                player1 = 2
                showToastFromPlayer1Choice(player1!!)
                setSelectPlayer(2)
                if (playMode != 0) {
                    random = Random.nextInt(0, 3)
                    gamePlay(player1!!, random)
                }
            }
        }
    }
    private fun onPlayerTwoClick() {
        binding.flActionCpuRock.setOnClickListener {
            if (!isGameFinished) {
                player2 = 0
                gamePlay(player1!!, player2!!)
            }
        }
        binding.flActionCpuPapper.setOnClickListener {
            if (!isGameFinished) {
                player2 = 1
                gamePlay(player1!!, player2!!)
            }
        }
        binding.flActionCpuScissor.setOnClickListener {
            if (!isGameFinished){
                player2 = 2
            gamePlay(player1!!, player2!!)
        }
        }
    }

    private fun gamePlay(playerOne: Int, playerTwo: Int) {
        if ((playerOne.plus(1)).rem(3) == playerTwo) {
            Log.d(TAG, "setClickEvent Computer won")
            //belum ada image dan dialog ke mas ridwan
            binding.ivImageVs.setImageResource(R.drawable.icon_com_win)
            //DialogFragment(1, flag).show(supportFragmentManager, null)

        } else if (playerOne == playerTwo) {
            Log.d(TAG, "setClickEvent draw")
            //belum ada image dan dialog ke mas ridwan
            binding.ivImageVs.setImageResource(R.drawable.icon_draw)
            //DialogFragment(3, flag).show(supportFragmentManager, null)
        } else {
            Log.d(TAG, "setClickEvent User won")
            //belum ada image dan dialog ke mas ridwan
            binding.ivImageVs.setImageResource(R.drawable.icon_winner)
            //DialogFragment(0, flag).show(supportFragmentManager, null)

        }
        isGameFinished = true
        setSelectPlayer(playerOne)
        setSelectComputer(playerTwo)
        if (playMode != 0) {
            showToastFromPlayer2Choice(getString(R.string.text_player_cpu), playerTwo)
        } else {
            showToastFromPlayer2Choice(
                getString(R.string.text_player_player2),
                playerTwo
            )
        }
        setControl()
    }

    fun resetGame() {
        setSelectPlayer(-1)
        setSelectComputer(-1)
        player1 = null
        player2 = null
        binding.ivImageVs.setImageResource(R.drawable.icon_vs_gameplay)
        binding.flActionPlayerRock.isInvisible = false
        binding.flActionPlayerPapper.isInvisible = false
        binding.flActionPlayerScissor.isInvisible = false
    }

    private fun showToastFromPlayer1Choice(choice: Int) {
        if (choice == 0) {
            Toast.makeText(
                this,
                String.format(
                    getString(R.string.text_toast_choice),
                    UserPreference(this).userName,
                    getString(R.string.text_shape_rock)

                ), Toast.LENGTH_SHORT
            ).show()
        } else if (choice == 1) {
            Toast.makeText(
                this,
                String.format(
                    getString(R.string.text_toast_choice),
                    UserPreference(this).userName,
                    getString(R.string.text_shape_paper)
                ), Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                this,
                String.format(
                    getString(R.string.text_toast_choice),
                    UserPreference(this).userName,
                    getString(R.string.text_shape_scissor)
                ), Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun showToastFromPlayer2Choice(player: String, choice: Int) {
        if (choice == 0) {
            Toast.makeText(
                this,
                String.format(
                    getString(R.string.text_toast_choice),
                    player,
                    getString(R.string.text_shape_rock)
                ), Toast.LENGTH_SHORT
            ).show()
        } else if (choice == 1) {
            Toast.makeText(
                this,
                String.format(
                    getString(R.string.text_toast_choice),
                    player,
                    getString(R.string.text_shape_paper)
                ), Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                this,
                String.format(
                    getString(R.string.text_toast_choice),
                    player,
                    getString(R.string.text_shape_scissor)
                ), Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setControl(){
        if(onPlayerOneClick() == onPlayerOneClick()){
            binding.flActionPlayerRock.isInvisible = true
            binding.flActionPlayerPapper.isInvisible = true
            binding.flActionPlayerScissor.isInvisible = true
        }else {
            binding.flActionPlayerRock.isInvisible = false
            binding.flActionPlayerPapper.isInvisible = false
            binding.flActionPlayerScissor.isInvisible = false
        }
    }

    private fun setSelectPlayer(playerMechanic: Int) {
        when (GameMechanic.formInt(playerMechanic)) {
            GameMechanic.ROCK -> {
                binding.flActionPlayerRock.setBackgroundResource(R.drawable.ic_bg_click)
                binding.flActionPlayerPapper.setBackgroundResource(0)
                binding.flActionPlayerScissor.setBackgroundResource(0)
            }
            GameMechanic.SCISSOR -> {
                binding.flActionPlayerRock.setBackgroundResource(0)
                binding.flActionPlayerPapper.setBackgroundResource(R.drawable.ic_bg_click)
                binding.flActionPlayerScissor.setBackgroundResource(0)
            }
            GameMechanic.PAPER -> {
                binding.flActionPlayerRock.setBackgroundResource(0)
                binding.flActionPlayerPapper.setBackgroundResource(0)
                binding.flActionPlayerScissor.setBackgroundResource(R.drawable.ic_bg_click)
            }
            else -> {
                binding.flActionPlayerRock.setBackgroundResource(0)
                binding.flActionPlayerPapper.setBackgroundResource(0)
                binding.flActionPlayerScissor.setBackgroundResource(0)
            }
        }
    }

    private fun setSelectComputer(compMechanic: Int) {
        when (GameMechanic.formInt(compMechanic)) {
            GameMechanic.ROCK -> {
                binding.flActionCpuRock.setBackgroundResource(R.drawable.ic_bg_click)
                binding.flActionCpuPapper.setBackgroundResource(0)
                binding.flActionCpuScissor.setBackgroundResource(0)
            }
            GameMechanic.SCISSOR -> {
                binding.flActionCpuRock.setBackgroundResource(0)
                binding.flActionCpuPapper.setBackgroundResource(R.drawable.ic_bg_click)
                binding.flActionCpuScissor.setBackgroundResource(0)
            }
            GameMechanic.PAPER -> {
                binding.flActionCpuRock.setBackgroundResource(0)
                binding.flActionCpuPapper.setBackgroundResource(0)
                binding.flActionCpuScissor.setBackgroundResource(R.drawable.ic_bg_click)
            }
            else -> {
                binding.flActionCpuRock.setBackgroundResource(0)
                binding.flActionCpuPapper.setBackgroundResource(0)
                binding.flActionCpuScissor.setBackgroundResource(0)
            }
        }
    }
}