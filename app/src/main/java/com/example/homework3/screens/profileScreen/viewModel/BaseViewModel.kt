package com.example.homework2.viewModel

import androidx.lifecycle.ViewModel
import com.example.homework2.models.Action
import com.example.homework2.models.State
import kotlinx.coroutines.flow.StateFlow


abstract class BaseViewModel<A : Action, S : State> : ViewModel() {
    abstract val state: StateFlow<S>
    abstract fun onAction(action: A)
}