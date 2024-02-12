package io.billie.orders.app.domain.entities

import io.billie.orders.app.domain.vo.Item
import java.math.BigDecimal
import java.util.UUID

data class Shipment(
    val id: ShipmentId,
    val total: BigDecimal,
    val shippedItems: List<Item>? = null,
    val order: Order? = null,
) {

    data class ShipmentId(override val value: UUID) : Identity {
    }

    companion object Factory {
        fun new(total: Double): Shipment {
            return Shipment(ShipmentId(UUID.randomUUID()), total.toBigDecimal())
        }

        fun new(total: Double, order: Order?, items: List<Item>?): Shipment {
            return Shipment(ShipmentId(UUID.randomUUID()), total.toBigDecimal(), items, order)
        }
    }
}
