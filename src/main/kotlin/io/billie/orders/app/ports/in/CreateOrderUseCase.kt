package io.billie.orders.app.ports.`in`

import io.billie.orders.app.domain.entities.Order
import io.billie.orders.app.ports.`in`.commands.NewOrderCommand

interface CreateOrderUseCase {

    fun createOrder(newOrderCommand: NewOrderCommand): Order
}