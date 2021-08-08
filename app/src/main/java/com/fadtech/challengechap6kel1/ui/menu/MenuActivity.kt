package com.fadtech.challengechap6kel1.ui.menu

import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fadtech.challengechap6kel1.R
import com.fadtech.challengechap6kel1.data.constant.Constant
import com.fadtech.challengechap6kel1.databinding.ActivityMenuBinding
import com.fadtech.challengechap6kel1.preference.UserPreference
import com.fadtech.challengechap6kel1.ui.dialog.DialogHowtoplayFragment
import com.fadtech.challengechap6kel1.ui.dialog.DialogSettingFragment
import com.fadtech.challengechap6kel1.ui.main.MainActivity
import com.fadtech.challengechap6kel1.ui.ranking.RankingActivity
import com.google.android.material.snackbar.Snackbar

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    // Maximumn sound stream.
    private val MAX_STREAMS = 1

    //Soundpool as player
    private lateinit var soundPool: SoundPool

    //Loaded is state of sound loaded into soundpool
    private var loaded = false

    //Sound ID is id created by SoundPool
    // And Sound ID needed to play the sound
    private var soundId = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        supportActionBar!!.hide()
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTextMenuWelcome()
        showWelcomeMessage()
        setClickListenersPlayerPlayer()
        versionSetUpSound()
        soundEffectListener()

    }



    private fun setTextMenuWelcome() {
        binding.tvTitleNamePlayerOne.text = String.format(
            getString(R.string.text_menu_welcome),
            UserPreference(this).userNamePlayerOne
        )
    }

    private fun showWelcomeMessage() {
        val snackBar = Snackbar.make(
            binding.root,
            getString(R.string.text_hello_snackbar) + UserPreference(this).userNamePlayerOne,
            Snackbar.LENGTH_SHORT
        )
        snackBar.setAction(getString(R.string.text_close_snackbar)) {
            snackBar.dismiss()
        }
        snackBar.show()
    }

    private fun setClickListenersPlayerPlayer() {
        binding.btnImgVsCom.setOnClickListener {
            val intent = Intent(this@MenuActivity, MainActivity::class.java).apply {
                putExtra(Constant.PLAY_MODE, 1)
            }
            startActivity(intent)
            setupSoundEffect()
        }

        binding.btnImgVsPlayer.setOnClickListener {
            startActivity(Intent(this, InputNamePlayerTwoActivity::class.java))

        }

        binding.btnImgHowtoplay.setOnClickListener{
            //Dialog Setting Dialog Click Listener
            DialogHowtoplayFragment().show(supportFragmentManager, null)
            setupSoundEffect()
        }
    }

    private fun soundEffectListener(){

        binding.ivSettingMenu.setOnClickListener {
            setupSoundEffect()
        }

        binding.btnImgVsPlayer.setOnClickListener {
            setupSoundEffect()
        }

        binding.btnImgMenuRank.setOnClickListener {
            setupSoundEffect()
        }

        binding.btnImgMenuHistory.setOnClickListener {
            setupSoundEffect()
        }

        binding.btnImgHowtoplay.setOnClickListener {

        }

    }

    private fun versionSetUpSound(){
        // For Android SDK >= 21
        if (Build.VERSION.SDK_INT >= 21) {
            val audioAttrib = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
            val builder = SoundPool.Builder()
            builder.setAudioAttributes(audioAttrib).setMaxStreams(MAX_STREAMS)
            this.soundPool = builder.build()
        } else {
            // SoundPool(int maxStreams, int streamType, int srcQuality)
            this.soundPool = SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0)
        }
    }


    private fun setupSoundEffect(){

        this.soundPool.setOnLoadCompleteListener { soundPool, i, i2 ->
            loaded = true
        }

        this.soundId = this.soundPool.load(this, R.raw.sound_effect_click_button_japan, 1)

        //Get Sound Settings From System
        val mgr = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val actualVolume = mgr.getStreamVolume(AudioManager.STREAM_MUSIC).toFloat()
        val maxVolume = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC).toFloat()
        val volume = actualVolume / maxVolume

        if(loaded){
            val streamId = this.soundPool.play(this.soundId, volume, volume, 1, 0, 1f)
        }else{
            Toast.makeText(this, "Soundpool Not Loaded", Toast.LENGTH_LONG).show()
        }
    }


}
