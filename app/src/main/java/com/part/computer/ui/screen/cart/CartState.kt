package com.part.computer.ui.screen.cart

import com.part.computer.model.OrderPart

data class CartState(
    val orderPart: List<OrderPart>,
    val totalRequiredPoint: Int
)