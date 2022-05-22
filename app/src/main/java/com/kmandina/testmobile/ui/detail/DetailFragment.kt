package com.kmandina.testmobile.ui.detail

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.LayoutAnimationController
import android.view.animation.TranslateAnimation
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kmandina.testmobile.R
import com.kmandina.testmobile.databinding.FragmentDetailBinding
import com.kmandina.testmobile.ui.dashboard.MovieAdapter
import com.kmandina.testmobile.utils.InjectorUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLSession

class DetailFragment : Fragment(), MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, SurfaceHolder.Callback {

    private val args: DetailFragmentArgs by navArgs()

    private val viewmodelM: DetailViewModel by viewModels {
        InjectorUtils.provideMovieDetailViewModelFactory(requireContext(), args.movieId)
    }

    private lateinit var uiBind: FragmentDetailBinding

    var mediaPlayer: MediaPlayer? = null
    var surfaceHolder: SurfaceHolder? = null
    var pause = false
    var path: String = ""
    var savePos = 0
    var mHandler = Handler()

    var r: Runnable = object : Runnable {
        override fun run() {
            if (mediaPlayer != null) {
                uiBind.sbPlay.progress = mediaPlayer!!.currentPosition
            }
            mHandler.postDelayed(this, 1000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        uiBind = DataBindingUtil.inflate<FragmentDetailBinding>(
            inflater, R.layout.fragment_detail, container, false
        ).apply {
            viewmodel = viewmodelM
            lifecycleOwner = viewLifecycleOwner

            toolbar.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }
        }
        setHasOptionsMenu(true)

        context ?: return uiBind.root

        return uiBind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getPath()

        surfaceHolder = uiBind.vv.holder
        surfaceHolder!!.addCallback(this)

        uiBind.vv.setOnClickListener { v: View? ->
            if(mediaPlayer != null) {
                if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
                    mediaPlayer!!.pause()
                    uiBind.play.setImageResource(R.drawable.play)
                    show()
                } else {
                    mediaPlayer!!.start()
                    uiBind.play.setImageResource(R.drawable.pause)
                    hide()
                }
            }
        }

        uiBind.play.setOnClickListener { v: View? ->
            Log.d("View Holder", "Play ")
            if(mediaPlayer != null){
                if (mediaPlayer!!.isPlaying) {
                    mediaPlayer!!.pause()
                    uiBind.play.setImageResource(R.drawable.play)
                } else {
                    mediaPlayer!!.start()
                    uiBind.play.setImageResource(R.drawable.pause)
                }
            }
        }

        uiBind.sbPlay.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer!!.seekTo(progress * 1000)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                if(mediaPlayer != null) {
                    mediaPlayer!!.seekTo(seekBar.progress)
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                if(mediaPlayer != null) {
                    mediaPlayer!!.seekTo(seekBar.progress)
                }
            }
        })


    }


    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer != null) {
            mediaPlayer!!.release()
            mediaPlayer = null
        }
        mHandler.removeCallbacks(r)
    }

    override fun onPause() {
        super.onPause()
        if (!pause && mediaPlayer != null) {
            mediaPlayer!!.pause()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!pause && mediaPlayer != null) {
            mediaPlayer!!.start()
        }
    }

    private fun playVideo(dir: String) {
        try {
            pause = false
            mediaPlayer = MediaPlayer()

            mediaPlayer!!.setDataSource(dir)

            mediaPlayer!!.setDisplay(surfaceHolder)
            mediaPlayer!!.prepare()
            uiBind.sbPlay.max = mediaPlayer!!.duration
            r.run()
            // mMediaPlayer.prepareAsync(); Para streaming
            mediaPlayer!!.setOnBufferingUpdateListener(this)
            mediaPlayer!!.setOnCompletionListener(this)
            mediaPlayer!!.setOnPreparedListener(this)
            mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mediaPlayer!!.seekTo(savePos)
            uiBind.play.setImageResource(R.drawable.pause)
        } catch (e: Exception) {
            Log.d("playVideo", "ERROR: " + e.message)
        }
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        Log.d("surfaceCreated", "surfaceCreated called")
        Log.d("playVideo", path)
        playVideo(path)
    }


    override fun surfaceChanged(p0: SurfaceHolder, format: Int, width: Int, height: Int) {
        Log.d("surfaceChanged", "surfaceChanged called")
    }


    override fun surfaceDestroyed(p0: SurfaceHolder) {
        Log.d("surfaceDestroyed", "surfaceDestroyed called")
    }


    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
        Log.d("onBufferingUpdate", "onBufferingUpdate percent:$percent")
    }


    override fun onCompletion(mp: MediaPlayer?) {
        Log.d("onCompletion", "onCompletion called")
    }


    override fun onPrepared(mp: MediaPlayer?) {
        Log.d("onPrepared", "onPrepared called")
        val mVideoWidth = mediaPlayer!!.videoWidth
        val mVideoHeight = mediaPlayer!!.videoHeight
        if (mVideoWidth != 0 && mVideoHeight != 0) {
            surfaceHolder!!.setFixedSize(mVideoWidth, mVideoHeight)
            mediaPlayer!!.start()
        }
    }

    fun onPointerCaptureChanged(hasCapture: Boolean) {}


    fun show() {
        if (uiBind.ButonsLayout.visibility == View.GONE) {
            animar(true)
            uiBind.ButonsLayout.visibility = View.VISIBLE
        }
    }

    fun hide() {
        if (uiBind.ButonsLayout.visibility == View.VISIBLE) {
            animar(false)
            uiBind.ButonsLayout.visibility = View.GONE
        }
    }

    private fun animar(mostrar: Boolean) {
        val set = AnimationSet(true)
        val animation: Animation = if (mostrar) {
            //desde la esquina inferior derecha a la superior izquierda
            TranslateAnimation(
                Animation.RELATIVE_TO_SELF,
                1.0f,
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                1.0f,
                Animation.RELATIVE_TO_SELF,
                0.0f
            )
        } else {    //desde la esquina superior izquierda a la esquina inferior derecha
            TranslateAnimation(
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                1.0f,
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                1.0f
            )
        }
        //duración en milisegundos
        animation.setDuration(500)
        set.addAnimation(animation)
        val controller = LayoutAnimationController(set, 0.25f)
        uiBind.ButonsLayout.layoutAnimation = controller
        uiBind.ButonsLayout.startAnimation(animation)
    }

    private fun getPath() {

        CoroutineScope(Dispatchers.IO).launch {
            val path = viewmodelM.getTrailerPath()
            withContext(Dispatchers.Main) {
                playVideo(path)
            }
        }

    }


    override fun onStart() {
        super.onStart()


        if ((activity as AppCompatActivity?)!!.supportActionBar != null) {
            (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        }
        (activity as AppCompatActivity).findViewById<BottomNavigationView>(R.id.nav_view).visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        if ((activity as AppCompatActivity?)!!.supportActionBar != null) {
            (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        }
        (activity as AppCompatActivity).findViewById<BottomNavigationView>(R.id.nav_view).visibility = View.VISIBLE
    }

}