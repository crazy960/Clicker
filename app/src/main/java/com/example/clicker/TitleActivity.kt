package com.example.clicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle;

class TitleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title);

        val view : TitleSurfaceView = findViewById(R.id.backgroundView);

    }
}