package com.example.championsapplication.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.championsapplication.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChampionsMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}