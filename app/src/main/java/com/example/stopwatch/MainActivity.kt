package com.example.stopwatch

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

class MainActivity : AppCompatActivity() {
    lateinit var arrow : ShapeableImageView
    lateinit var start: ImageButton
    lateinit var pause: ImageButton
    lateinit var reset: ImageButton
    lateinit var timer: Chronometer
    lateinit var anim: ObjectAnimator
    lateinit var controlLayout:LinearLayout
    lateinit var handler: Handler
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerView: RecyclerView
    lateinit var lapTimeAdapater: LapTimeAdapater


    var tUpdate=0L
    var tBuff=0L
    var tmilisec=0L
    var tStart=0L
    var min:Int = 0
    var sec:Int = 0
    var milisec:Int = 0

    val lapTimeList= mutableListOf<LapTimeModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arrow=findViewById(R.id.ClockNeedle)
        start=findViewById(R.id.startStopWatch)

        controlLayout=findViewById(R.id.controlLayout)
        pause=findViewById(R.id.pauseStopWatch)
        reset=findViewById(R.id.resetStopWatch)
        timer=findViewById(R.id.timer)


        layoutManager = LinearLayoutManager(this)
        recyclerView=findViewById(R.id.recyclerView)
        lapTimeAdapater = LapTimeAdapater(this, lapTimeList.asReversed())
        recyclerView.adapter = lapTimeAdapater
        recyclerView.layoutManager = layoutManager

        handler= Handler()

        anim =ObjectAnimator.ofFloat(arrow, View.ROTATION, -360f, 0f)
        anim.duration=1000
        anim.repeatCount=ObjectAnimator.INFINITE


        start.setOnClickListener ( View.OnClickListener {
            anim.start()
            start.visibility=View.GONE
            controlLayout.visibility=View.VISIBLE
            tStart=SystemClock.uptimeMillis()
            handler.postDelayed(runnable,0)
            timer.start()
            reset.tag="lap"
            reset.setImageResource(R.drawable.ic_action_lap)
        })

        pause.setOnClickListener(
            View.OnClickListener {
                val tag=pause.tag
             if(tag=="unpause")
             {
                 anim.resume()
                 reset.tag="lap"
                 reset.setImageResource(R.drawable.ic_action_lap)
                 pause.tag="pause"
                 tStart=SystemClock.uptimeMillis()
                 handler.postDelayed(runnable,0)
                 timer.start()
                 pause.setImageResource(R.drawable.ic_action_pause)



             }

                else if(tag=="pause")
             {
                 anim.pause()
                 reset.tag="reset"
                 reset.setImageResource(R.drawable.ic_action_reset)
                 pause.tag="unpause"
                 tBuff+=tmilisec
                 handler.removeCallbacks(runnable)
                 timer.stop()
                 pause.setImageResource(R.drawable.ic_action_play)

             }
            })

        reset.setOnClickListener(
            View.OnClickListener {
                if (reset.tag == "reset")
                {
                    anim.start()
                anim.pause()
                controlLayout.visibility = View.GONE
                start.visibility = View.VISIBLE
                    pause.tag="pause"
                    pause.setImageResource(R.drawable.ic_action_pause)
                tUpdate = 0L
                tBuff = 0L
                tmilisec = 0L
                tStart = 0L
                min = 0
                sec = 0
                milisec = 0
                timer.text = "00:00:00"
                timer.stop()
                    lapTimeList.clear()
                    lapTimeAdapater.notifyDataSetChanged()
            }
                else
                {
                    val laptimeObject=LapTimeModel(timer.text.toString())
                    lapTimeList.add(laptimeObject)
                    lapTimeAdapater.notifyDataSetChanged()
                }

            })
    }


    private val runnable: Runnable = object : Runnable {
        override fun run() {
            tmilisec=SystemClock.uptimeMillis() - tStart
            tUpdate=tBuff + tmilisec
            sec= (tUpdate/1000).toInt()
            min=sec / 60
            sec %= 60
            milisec= (tUpdate % 100).toInt()
            timer.text=String.format("%02d",min)+":"+String.format("%02d",sec)+":"+String.format("%02d",milisec)
            handler.postDelayed(this,60)
        }
    }
}