package io.billie.orders.app.domain.entities

import io.billie.orders.app.domain.vo.Item
import java.util.UUID

data class Shipment(
    val id: UUID,
    val orderId: UUID,
    val shippedItems: List<Item>
) {

    companion object Factory {

        fun new(orderId: UUID, items: List<Item>): Shipment {
            return Shipment(UUID.randomUUID(), orderId, items)
        }
    }
}
