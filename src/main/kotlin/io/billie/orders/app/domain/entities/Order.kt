package io.billie.orders.app.domain.entities

import io.billie.orders.app.domain.vo.Item
import io.billie.orders.app.domain.vo.OrderStatus
import java.util.UUID

data class Order(
    val id: UUID,
    val merchantId: UUID,
    val items: List<Item> = listOf(),
    val total: Double = 0.0,
    val status: OrderStatus
) {
    companion object Factory {

        fun new(merchantId: UUID, items: List<Item>, total: Double): Order {
            return Order(UUID.randomUUID(), merchantId, items, total, OrderStatus.NOT_SHIPPED)
        }
    }
}
