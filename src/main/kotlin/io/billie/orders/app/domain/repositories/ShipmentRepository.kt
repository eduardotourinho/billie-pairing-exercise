package io.billie.orders.app.domain.repositories

import java.util.UUID

interface ShipmentRepository {

    fun addShippedItems(orderId: UUID, items: List<UUID>)
}