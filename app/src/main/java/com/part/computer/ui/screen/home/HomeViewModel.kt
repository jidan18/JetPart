package com.part.computer.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.part.computer.data.PartRepository
import com.part.computer.model.OrderPart
import com.part.computer.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: PartRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<OrderPart>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<OrderPart>>>
        get() = _uiState

    fun getAllPart() {
        viewModelScope.launch {
            repository.getAllRewards()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderRewards ->
                    _uiState.value = UiState.Success(orderRewards)
                }
        }
    }
}