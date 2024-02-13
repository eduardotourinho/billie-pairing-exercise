package io.billie.orders.app.ports.`in`

import io.billie.orders.app.domain.entities.Order
import java.util.*

interface CreateOrderUseCase {

    fun createOrder(newOrderCommand: NewOrderCommand): Order

    data class NewOrderCommand(val merchantId: UUID, val items: List<UUID>, val total: Double) {}
}
