package com.harunaktas.kennycatch

import android.R
import android.animation.ObjectAnimator
import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.harunaktas.kennycatch.databinding.ActivityMainBinding
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var userYes: Boolean = true
    var score: Int = 0
    var runnable = Runnable{}
    var handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.restart.visibility = View.INVISIBLE
        binding.cancel.visibility = View.INVISIBLE

        kennyMove()

        var timer = object : CountDownTimer(5000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                binding.textView.text = "Time: ${millisUntilFinished / 1000}"

            }
            override fun onFinish() {
                binding.textView.text = "Time: 0"
                handler.removeCallbacks(runnable)
                binding.kenny.visibility = View.INVISIBLE
                binding.restart.visibility = View.VISIBLE
                binding.cancel.visibility = View.VISIBLE
                val alert = AlertDialog.Builder(this@MainActivity)
                alert.setTitle("Game Over!")
                alert.setMessage("Restart the Game?")
                alert.setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {

                        userYes = true
                        //millisUntilFinished = 10000
                        score = 0
                        binding.textView2.text = "Score : ${score}"
                        //startCountDown()
                        kennyMove()
                        restart()
                        //Restart
                        /*val intent = intent
                        finish()
                        startActivity(intent)*/
                        //Toast.makeText(this@MainActivity,"Saved", Toast.LENGTH_LONG ).show()
                    }

                })
                alert.setNegativeButton("No") { po, p1 ->
                    userYes = false
                    finish()
                    //Toast.makeText(this@MainActivity, "Not Saved", Toast.LENGTH_LONG).show()
                }
                alert.show()
            }

        }.start()


    }
    fun restart(){
        val intent = intent
        finish()
        startActivity(intent)
    }

     fun kennyMove() {
        binding.kenny.visibility = View.VISIBLE
        runnable = object : Runnable {
            override fun run() {
                var x: Float = (-250..250).random().toFloat()
                var y: Float = (-250..250).random().toFloat()
                var z: Float = (-250..250).random().toFloat()
                var q: Float = (-250..250).random().toFloat()


                val animation = TranslateAnimation(x, y, z, q) // Von (0, 0) nach (200, 0)

                animation.duration = 400 // Dauer der Animation in Millisekunden

                animation.fillAfter = true // Bild am Endpunkt der Animation beibehalten


                binding.kenny.startAnimation(animation)

                handler.postDelayed(runnable, 400)
            }
        }
        handler.post (runnable)
    }


    override fun onStop() {
        super.onStop()
        println("onStop executed")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy executed")
    }

    fun kenny(view: View) {
        score++
        binding.textView2.text = "Score : ${score}"

    }

}