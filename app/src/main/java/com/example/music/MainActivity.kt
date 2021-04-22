package com.example.music

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.music.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val viewModel : MusicViewModel by lazy {
                ViewModelProvider(this,
                        ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(MusicViewModel::class.java)
            }

            binding.constraintLayout.setOnClickListener {
                removeFocus(it)
            }

            binding.recyclerView.layoutManager = GridLayoutManager(this, 2  )
            val adapter = MusicRVAdapter(this)
            binding.recyclerView.adapter = adapter

            binding.button.setOnClickListener {
                viewModel.getResponse(binding.editText.text.toString())
                removeFocus(binding.constraintLayout)
            }

            viewModel.songList.observe(this , { list ->

                list?.let {
                    adapter.updateList(list)
                }

            })
    }

    private fun removeFocus(view : View)
    {
        currentFocus?.clearFocus()
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}