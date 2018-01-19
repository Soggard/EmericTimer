package net.emericbr.emerictimer

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var isCountDownActive = false
    private lateinit var countDownTimer: CountDownTimer
    private var currentSeekBarPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.d(TAG,"Progress : " + progress.toString())
                updateTimer(progress, fromUser)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                Log.d(TAG,"onStartTrackingTouch")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Log.d(TAG,"onStopTrackingTouch")
            }

        })

        btnTimer.setOnClickListener { toggleTimer() }

        seekBar.max = 5 * 60
    }

    @SuppressLint("SetTextI18n")
    fun updateTimer(progress: Int, fromUser: Boolean) {
        val minutes = progress / 60
        val seconds = progress % 60
        timer.text = "${String.format("%02d", minutes)}:${String.format("%02d", seconds)}"

        if (!fromUser) {
            seekBar.progress = progress
        }
    }

    fun toggleTimer() {
        if (!isCountDownActive) {
            btnTimer.text = getString(R.string.reset)
            currentSeekBarPosition = seekBar.progress
            countDownTimer = object: CountDownTimer((seekBar.progress * 1000).toLong() + 100, 1000){
                override fun onFinish() {
                    // WOLOLO
                    resetTimer()
                }

                override fun onTick(millisUntilFinished: Long) {
                    //seekBar.progress = seekBar.progress - 1
                    //updateTimer(seekBar.progress)
                    updateTimer((millisUntilFinished / 1000).toInt(), false)
                }
            }

            countDownTimer.start()
            seekBar.isEnabled = false
            isCountDownActive = true

        } else {
            resetTimer()
        }

    }

    fun resetTimer() {
        countDownTimer.cancel()
        isCountDownActive = false
        btnTimer.text = getString(R.string.lancer)
        seekBar.isEnabled = true

        updateTimer(currentSeekBarPosition, false)
    }

    companion object {
        internal const val TAG = "MainActivity"
    }
}
