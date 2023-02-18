package com.example.mvi_demo

sealed class MainViewState {
    // Idle (didn't do any think)
    object Idle: MainViewState()
    // Number (result)
    data class Number(val number: Int): MainViewState()
    // Error
    data class Error(val error: String): MainViewState()
    // Loading if you call API
}
