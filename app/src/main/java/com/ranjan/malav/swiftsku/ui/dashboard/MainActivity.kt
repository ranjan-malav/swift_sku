package com.ranjan.malav.swiftsku.ui.dashboard

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ranjan.malav.swiftsku.data.local.AppDatabase
import com.ranjan.malav.swiftsku.data.repository.PriceBookRepository
import com.ranjan.malav.swiftsku.data.repository.TransactionRepository
import com.ranjan.malav.swiftsku.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}