package com.example.task3

import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    var isReady:Boolean = false
    var tts:TextToSpeech? = null
    var m: MediaPlayer? = null
    var isPlaying : Boolean = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        m = MediaPlayer();
         tts= TextToSpeech(this, TextToSpeech.OnInitListener {
            isReady = true
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.music){
            Toast.makeText(this,"play music",Toast.LENGTH_SHORT).show()
            if(!isPlaying) {
                try {
                    m!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
                    val u : Uri = Uri.parse("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3")
                    m!!.setDataSource(this, u)
                    m!!.prepareAsync()
                    m!!.setOnPreparedListener {
                        m!!.start()
                    }

                    isPlaying = true;
                }
                catch(e:Exception){
                    Log.d("media player", e.printStackTrace().toString())
                }

            }
            else{
                m!!.stop()
                m!!.release()
                m!!.reset()
                isPlaying = false;
            }
        }
        else if(item.itemId == R.id.speak){

            if(isReady) {
                val txt: EditText = findViewById(R.id.editTextTextPersonName)
                tts!!.speak(txt.text.toString(), TextToSpeech.QUEUE_ADD, null)
                Toast.makeText(this, "speak", Toast.LENGTH_SHORT).show()
            }
        }
        return true;
    }
}