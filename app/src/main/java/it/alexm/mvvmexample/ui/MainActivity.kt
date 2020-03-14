package it.alexm.mvvmexample.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import it.alexm.mvvmexample.R
import it.alexm.mvvmexample.ui.single_movie_details.SingleMovieActivity
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Created by alexm on 13/03/2020
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            startActivity(
                Intent(this, SingleMovieActivity::class.java).apply {
                    putExtra("id", 495764)
                }
            )
        }
    }
}