package com.example.clicker

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle;
import android.util.Log
import android.view.MotionEvent

class TitleActivity : AppCompatActivity() {

    private lateinit var view : TitleSurfaceView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_title);

        view  = findViewById(R.id.backgroundView);


    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("TitleActivity","Top")
        view.getGun().Shoot()
        return super.onTouchEvent(event)
    }

}