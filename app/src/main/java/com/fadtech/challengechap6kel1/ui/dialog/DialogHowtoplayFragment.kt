package com.fadtech.challengechap6kel1.ui.dialog

import android.app.Dialog
import android.content.pm.ActivityInfo
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.fadtech.challengechap6kel1.R
import com.fadtech.challengechap6kel1.data.constant.Constant
import com.fadtech.challengechap6kel1.databinding.FragmentDialogHowtoplayBinding
import com.google.android.material.snackbar.Snackbar
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener


class DialogHowtoplayFragment : DialogFragment() {

    private lateinit var binding: FragmentDialogHowtoplayBinding
    private lateinit var player: YouTubePlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDialogHowtoplayBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_dialog_round_white)

        binding.ivBtnCloseDialogHowtoplay.setOnClickListener{
            dialog?.dismiss()

        }

        lifecycle.addObserver(binding.youtubePlayerView)
        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                this@DialogHowtoplayFragment.player = youTubePlayer
                player.loadVideo(Constant.VIDEO_ID_YOUTUBE_HOWTOPLAY,0F)
            }
        })

        binding.youtubePlayerView.addFullScreenListener(object : YouTubePlayerFullScreenListener{
            override fun onYouTubePlayerEnterFullScreen() {
                showMessage("FullScreen Mode")
                enterFullScreen()
            }

            override fun onYouTubePlayerExitFullScreen() {
                showMessage("Exit FullSceen")
                exitFullScreen()
            }
        })

    }

    private fun enterFullScreen(){
        dialog?.let {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            it.window?.setLayout(width, height)
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        binding.tvTiteDialogHowtoplay.visibility = View.GONE
        binding.ivBtnCloseDialogHowtoplay.visibility = View.GONE
    }

    private fun exitFullScreen(){
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding.tvTiteDialogHowtoplay.visibility = View.VISIBLE
        binding.ivBtnCloseDialogHowtoplay.visibility = View.VISIBLE
    }


    private fun showMessage(msg:String){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    }



}