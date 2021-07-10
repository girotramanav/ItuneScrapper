package com.example.music

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.music.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel : MusicViewModel by lazy {
        ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(MusicViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.constraintLayout.setOnClickListener {
                removeFocus(it)
            }

            binding.recyclerView.layoutManager = GridLayoutManager(this, 2  )
            val adapter = MusicRVAdapter(this)
            binding.recyclerView.adapter = adapter

            binding.button.setOnClickListener {
                viewModel.getResponse(binding.editText.text.toString())
                removeFocus(binding.constraintLayout)
                binding.loadingBar.visibility = View.VISIBLE
            }

            viewModel.songList.observe(this , { list ->

                list?.let {
                    adapter.updateList(list)
                }
                binding.loadingBar.visibility = View.GONE

            })
    }

    private fun removeFocus(view : View)
    {
        currentFocus?.clearFocus()
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    fun reloadFromNetwork(item: MenuItem) {
        viewModel.getResponseFromNetwork(binding.editText.text.toString())
    }
}