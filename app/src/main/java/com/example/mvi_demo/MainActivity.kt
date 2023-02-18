package com.example.mvi_demo

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mvi_demo.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this)[AddNumberViewModel::class.java] }
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Log.i("bobobo", "onCreate: bobob")
        // render data
        render()
        binding.addNumberBtn.setOnClickListener {
            // Send Action
            lifecycleScope.launch {
                viewModel.intentChannel.send(MainIntent.AddNumber)
            }
        }
    }

    // to render view by data that observed from view model
    private fun render() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                when (it) {
                    is MainViewState.Idle -> binding.numberTxv.text = "Idle"
                    is MainViewState.Number -> binding.numberTxv.text = it.number.toString()
                    is MainViewState.Error -> binding.numberTxv.text = it.error
                }
            }

        }
    }
}