package com.part.computer.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.part.computer.data.PartRepository
import com.part.computer.model.OrderPart
import com.part.computer.model.thePart
import com.part.computer.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailPartViewModel(
    private val repository: PartRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<OrderPart>>(UiState.Loading)
    val uiState: StateFlow<UiState<OrderPart>> get() = _uiState

    /**
     * Fetches a specific part by its ID and updates the UI state accordingly.
     */
    fun getPartById(rewardId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderRewardById(rewardId))
        }
    }

    /**
     * Adds the specified part to the cart with the given count.
     */
    fun addToCart(thePart: thePart, count: Int) {
        viewModelScope.launch {
            repository.updateOrderReward(thePart.id, count)
        }
    }
}
