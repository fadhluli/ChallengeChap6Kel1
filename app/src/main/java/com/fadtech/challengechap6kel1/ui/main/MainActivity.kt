package com.fadtech.challengechap6kel1.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import com.fadtech.challengechap6kel1.R
import com.fadtech.challengechap6kel1.base.GenericViewModelFactory
import com.fadtech.challengechap6kel1.data.constant.Constant
import com.fadtech.challengechap6kel1.data.local.room.UserRoomDatabase
import com.fadtech.challengechap6kel1.data.local.room.datasource.UserDataSource
import com.fadtech.challengechap6kel1.data.model.User
import com.fadtech.challengechap6kel1.data.network.datasource.HistoryDataSource
import com.fadtech.challengechap6kel1.data.network.entity.requests.HistoryRequest
import com.fadtech.challengechap6kel1.data.network.service.HistoryApiServices
import com.fadtech.challengechap6kel1.databinding.ActivityMainBinding
import com.fadtech.challengechap6kel1.enum.GameMechanic
import com.fadtech.challengechap6kel1.preference.SessionPreference
import com.fadtech.challengechap6kel1.preference.UserPreference
import com.fadtech.challengechap6kel1.ui.dialog.DialogFragmentListener
import com.fadtech.challengechap6kel1.ui.dialog.DialogResultFragment
import com.fadtech.challengechap6kel1.ui.dialog.DialogSettingFragment
import com.fadtech.challengechap6kel1.ui.history.HistoryRepository
import com.fadtech.challengechap6kel1.ui.history.HistoryViewModel
import com.fadtech.challengechap6kel1.ui.ranking.RankingActivity
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity(), DialogFragmentListener, MainContract.View {
    private lateinit var binding: ActivityMainBinding
    private var isGameFinished: Boolean = false
    private var playMode: Int? = null
    private var player2: Int? = null
    private var player1: Int? = null
    private var totalWinplayer1: Int = 0
    private var totalWinplayer2: Int = 0
    private val TAG = MainActivity::class.java.simpleName
    private lateinit var viewModel: MainViewModel
    private var user: User? = null
    private var isInsertUserToDb = true
    private lateinit var sessionPreference: SessionPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        initView()
        onResetClick()
        onSettingClick()
    }


    private fun start() {
        playMode = intent.getIntExtra(Constant.PLAY_MODE, 0)
        binding.tvNamePlayerOne.text = UserPreference(this).userNamePlayerOne

        if (playMode == 0) {
            binding.tvNameCpu.text = UserPreference(this).userNamePlayerTwo
            onPlayerOneClick()
            onPlayerTwoClick()
        } else {
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
            //Dialog Setting Dialog Click Listener
            DialogSettingFragment().show(supportFragmentManager, null)
        }
    }

    //ketika player1 klik
    private fun onPlayerOneClick() {
        var random: Int
        binding.flActionPlayerRock.setOnClickListener {
            if (!isGameFinished) {
                player1 = 0
                setSelectPlayer(0)
                showToastFromPlayer1Choice(player1)
                if (playMode == 1) {
                    random = Random.nextInt(0, 3)
                    gamePlay(player1, random)
                } else {
                    hidePlayerOne()
                }
            }
        }
        binding.flActionPlayerPapper.setOnClickListener {
            if (!isGameFinished) {
                player1 = 1
                setSelectPlayer(1)
                showToastFromPlayer1Choice(player1)
                if (playMode == 1) {
                    random = Random.nextInt(0, 3)
                    gamePlay(player1, random)
                } else {
                    hidePlayerOne()
                }
            }
        }
        binding.flActionPlayerScissor.setOnClickListener {
            if (!isGameFinished) {
                player1 = 2
                showToastFromPlayer1Choice(player1)
                setSelectPlayer(2)
                if (playMode == 1) {
                    random = Random.nextInt(0, 3)
                    gamePlay(player1, random)
                } else {
                    hidePlayerOne()
                }
            }
        }
    }

    //ketika player2 klik
    private fun onPlayerTwoClick() {
        binding.flActionCpuRock.setOnClickListener {
            if (!isGameFinished) {
                player2 = 0
                gamePlay(player1, player2)
            }
        }
        binding.flActionCpuPapper.setOnClickListener {
            if (!isGameFinished) {
                player2 = 1
                gamePlay(player1, player2)
            }

        }
        binding.flActionCpuScissor.setOnClickListener {
            if (!isGameFinished) {
                player2 = 2
                gamePlay(player1, player2)
            }
        }
    }

    //nentuin siapa yg menang
    private fun gamePlay(playerOne: Int?, playerTwo: Int?) {
        if (playerOne != null) {
            if ((playerOne.plus(1)).rem(3) == playerTwo) {
                Log.d(TAG, "setClickEvent Computer won")
                totalWinplayer2 += 1
                binding.tvScorePlayerCpu.setText(totalWinplayer2.toString())
                binding.ivImageVs.setImageResource(R.drawable.icon_com_win)
                //Dialog Result for player 2 and Computer win
                if (playMode == 0) {
                    DialogResultFragment(
                        UserPreference(this).userNamePlayerTwo + " WINNER"
                    ).show(
                        supportFragmentManager,
                        null
                    )
                } else {
                    DialogResultFragment("COM WINNER").show(supportFragmentManager, null)
                }
                showPlayerOne()
                insertHistoryToAPI("Opponent Win")
            } else if (playerOne == playerTwo) {
                Log.d(TAG, "setClickEvent draw")
                binding.ivImageVs.setImageResource(R.drawable.icon_draw)
                //Dialog Result for Draw
                DialogResultFragment("Draw").show(supportFragmentManager, null)
                showPlayerOne()
                insertHistoryToAPI("Draw")

            } else {
                Log.d(TAG, "setClickEvent User won")
                totalWinplayer1 += 1
                binding.tvScorePlayerOne.setText(totalWinplayer1.toString())
                binding.ivImageVs.setImageResource(R.drawable.icon_winner)
                //Dialog Result for Player One
                DialogResultFragment(UserPreference(this).userNamePlayerOne + " WINNER").show(
                    supportFragmentManager,
                    null
                )
                showPlayerOne()
                insertHistoryToAPI("Player Win")
            }
            isGameFinished = true
            setSelectPlayer(playerOne)
            if (playerTwo != null) {
                setSelectComputer(playerTwo)
                if (playMode != 0) {
                    showToastFromPlayer2Choice(getString(R.string.text_player_cpu), playerTwo)
                } else {
                    showToastFromPlayer2Choice(
                        getString(R.string.text_player_player2),
                        playerTwo
                    )
                }
            }
        } else {
            Toast.makeText(
                this,
                String.format(
                    getString(R.string.text_toast_choose_attention),
                    UserPreference(this).userNamePlayerOne
                ),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    internal fun resetGame() {
        setSelectPlayer(-1)
        setSelectComputer(-1)
        player1 = null
        player2 = null
        binding.ivImageVs.setImageResource(R.drawable.icon_vs_gameplay)
        binding.flActionPlayerRock.isInvisible = false
        binding.flActionPlayerPapper.isInvisible = false
        binding.flActionPlayerScissor.isInvisible = false
    }

    private fun showToastFromPlayer1Choice(choice: Int?) {
        if (choice == 0) {
            Toast.makeText(
                this,
                String.format(
                    getString(R.string.text_toast_choice),
                    UserPreference(this).userNamePlayerOne,
                    getString(R.string.text_shape_rock)

                ), Toast.LENGTH_SHORT
            ).show()
        } else if (choice == 1) {
            Toast.makeText(
                this,
                String.format(
                    getString(R.string.text_toast_choice),
                    UserPreference(this).userNamePlayerOne,
                    getString(R.string.text_shape_paper)
                ), Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                this,
                String.format(
                    getString(R.string.text_toast_choice),
                    UserPreference(this).userNamePlayerOne,
                    getString(R.string.text_shape_scissor)
                ), Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun showToastFromPlayer2Choice(player: String, choice: Int?) {
        if (choice == 0) {
            Toast.makeText(
                this,
                String.format(
                    getString(R.string.text_toast_choice),
                    UserPreference(this).userNamePlayerTwo,
                    getString(R.string.text_shape_rock)
                ), Toast.LENGTH_SHORT
            ).show()
        } else if (choice == 1) {
            Toast.makeText(
                this,
                String.format(
                    getString(R.string.text_toast_choice),
                    UserPreference(this).userNamePlayerTwo,
                    getString(R.string.text_shape_paper)
                ), Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                this,
                String.format(
                    getString(R.string.text_toast_choice),
                    UserPreference(this).userNamePlayerTwo,
                    getString(R.string.text_shape_scissor)
                ), Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun hidePlayerOne() {
        binding.flActionPlayerRock.isInvisible = true
        binding.flActionPlayerPapper.isInvisible = true
        binding.flActionPlayerScissor.isInvisible = true
    }

    internal fun showPlayerOne() {
        binding.flActionPlayerRock.isInvisible = false
        binding.flActionPlayerPapper.isInvisible = false
        binding.flActionPlayerScissor.isInvisible = false
    }

    //add background for player1 choice
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

    //add background for player2 / computer choice
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

    private fun insertHistoryToAPI(result: String) {
        var mode: String?
        if (playMode == 0) {
            mode = "Multiplayer"
        } else {
            mode = "Singleplayer"
        }

        viewModel.insertHistory(
            HistoryRequest(
                mode = mode,
                result = result
            )
        )
    }

    // insert user to database
    fun insertUserToDb() {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        if(isInsertUserToDb){
            if (playMode == 0) {
                if (totalWinplayer1 > 0) {
                    user = User(
                        username = UserPreference(this).userNamePlayerOne.orEmpty(),
                        totalWin = totalWinplayer1,
                        date = currentDate
                    )
                    user?.let { viewModel.insertUser(it) }
                }
                if (totalWinplayer2 > 0) {
                    user = User(
                        username = UserPreference(this).userNamePlayerTwo.orEmpty(),
                        totalWin = totalWinplayer2,
                        date = currentDate
                    )
                    user?.let { viewModel.insertUser(it) }
                }
            } else {
                if (totalWinplayer1 > 0) {
                    user = User(
                        username = UserPreference(this).userNamePlayerOne.orEmpty(),
                        totalWin = totalWinplayer1,
                        date = currentDate
                    )
                    user?.let { viewModel.insertUser(it) }
                }
            }
            isInsertUserToDb = false
        }

    }

    fun navigateToRankingListActivity() {
        insertUserToDb()
        startActivity(Intent(this, RankingActivity::class.java))
    }


    override fun onBackPressed() {
        super.onBackPressed()
        insertUserToDb()
    }

    override fun onDialogDismiss() {
        resetGame()
        isGameFinished = false
    }

    override fun onSuccess() {
        //when save data success
        Toast.makeText(this, "Good Game!", Toast.LENGTH_SHORT).show()
    }

    override fun onFailed() {
        //when save data failed
        Toast.makeText(this, "Save Failed!", Toast.LENGTH_SHORT).show()
    }

    override fun initView() {
        start()
        initViewModel()
    }

    override fun initViewModel() {

        sessionPreference = SessionPreference(this)
        sessionPreference.authToken =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MTA5MmI5Y2Q0YTU2ZjAwMTdkYjE0M2QiLCJ1c2VybmFtZSI6ImFmZmFkZGQiLCJlbWFpbCI6ImFmZmFkZGRAZ21haWwuY29tIiwiaWF0IjoxNjI4MTQ3NjcxLCJleHAiOjE2MjgxNTQ4NzF9.yrBHl4-HEIpB_q1KV85Nyi4qRzZ8uVNurpbHcfMmFAw"
        val apiServices = HistoryApiServices.getInstance(sessionPreference)
        val userDataSource = UserDataSource(UserRoomDatabase.getInstance(this).userDao())
        apiServices?.let {
            val historyDataSource = HistoryDataSource(it)
            val repository = MainRepository(userDataSource, historyDataSource)
            viewModel = GenericViewModelFactory(MainViewModel(repository))
                .create(MainViewModel::class.java)
        }
//        val repository = MainRepository(dataSource)
//        viewModel =
//            GenericViewModelFactory(MainViewModel(repository)).create(MainViewModel::class.java)

        viewModel.transactionResult.observe(this, { isTransactionSuccess ->
            if (isTransactionSuccess) {
                onSuccess()
            } else {
                onFailed()
            }
        })

    }
}