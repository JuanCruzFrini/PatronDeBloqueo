package com.example.patrondebloqueo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.andrognito.patternlockview.PatternLockView
import com.andrognito.patternlockview.listener.PatternLockViewListener
import com.andrognito.patternlockview.utils.PatternLockUtils
import io.paperdb.Paper

class MainActivity : AppCompatActivity() {

    var save_pattern_key = "pattern_code"
    var final_pattern = ""
    var mPatternLockView: PatternLockView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Paper.init(this)
        val save_pattern = Paper.book().read<String>(save_pattern_key)

        if (save_pattern != null && save_pattern != "null") {
            setContentView(R.layout.activity_dos)
            mPatternLockView = findViewById<PatternLockView>(R.id.patron)
            mPatternLockView!!.addPatternLockListener(object : PatternLockViewListener {

                override fun onComplete(pattern: List<PatternLockView.Dot>) {

                    final_pattern = PatternLockUtils.patternToString(mPatternLockView, pattern)
                    if (final_pattern == save_pattern) {
                        Toast.makeText(this@MainActivity, "Password Correct!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@MainActivity, ActivityTres::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@MainActivity, "Password Incorrecta!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onStarted() {}
                override fun onProgress(progressPattern: List<PatternLockView.Dot>) {}
                override fun onCleared() {}
            })

        } else {
            setContentView(R.layout.activity_main)
            mPatternLockView = findViewById<View>(R.id.patron) as PatternLockView
            mPatternLockView!!.addPatternLockListener(object : PatternLockViewListener {

                override fun onComplete(pattern: List<PatternLockView.Dot>) {
                    final_pattern = PatternLockUtils.patternToString(mPatternLockView, pattern)
                }

                override fun onStarted() {}
                override fun onProgress(progressPattern: List<PatternLockView.Dot>) {}
                override fun onCleared() {}
            })

            val btnSetup = findViewById<View>(R.id.guardarPatronbtn) as Button
            btnSetup.setOnClickListener {
                Paper.book().write(save_pattern_key, final_pattern)
                Toast.makeText(this@MainActivity, "Save pattern okay!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MainActivity, ActivityDos::class.java)
                startActivity(intent)
            }
        }
    }
}