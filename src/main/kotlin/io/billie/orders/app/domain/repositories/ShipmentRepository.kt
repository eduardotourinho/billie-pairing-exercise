package io.billie.orders.app.domain.repositories

import io.billie.orders.app.domain.entities.Order
import io.billie.orders.app.domain.entities.Shipment
import java.util.UUID

interface ShipmentRepository {

    fun insertShipment(shipment: Shipment): Shipment

    fun findByOrder(orderId: Order.OrderId, merchantId: UUID): List<Shipment>
}