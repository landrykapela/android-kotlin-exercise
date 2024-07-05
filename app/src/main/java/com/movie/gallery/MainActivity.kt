package com.movie.gallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import androidx.room.RoomDatabase
import com.movie.gallery.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    @Inject
    lateinit var getHomePageTextTesting:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.homeText.text=getHomePageTextTesting

    }
}