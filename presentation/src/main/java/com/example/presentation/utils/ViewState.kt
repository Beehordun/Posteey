package com.example.presentation.utils

sealed class ViewState <T> (val data: T? = null, val message: String? = null)

class Loading<T> : ViewState<T>()
class Success<T>(data: T) : ViewState<T>(data)
class Error<T>(message: String?, data: T? = null) : ViewState<T>(data, message)