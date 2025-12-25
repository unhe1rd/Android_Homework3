package com.example.homework3.screens.profileScreen.viewModel

import androidx.lifecycle.ViewModel
import com.example.homework3.screens.profileScreen.models.Action
import com.example.homework3.screens.profileScreen.models.State
import kotlinx.coroutines.flow.StateFlow


abstract class BaseViewModel<A : Action, S : State> : ViewModel() {
    abstract val state: StateFlow<S>
    abstract fun onAction(action: A)
}