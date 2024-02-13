package io.billie.orders.app.ports.`in`

import io.billie.orders.app.domain.entities.Order
import io.billie.orders.app.domain.entities.Shipment
import java.util.*

interface NotifyOrderShipmentUseCase {

    fun notifyShipment(command: ShipmentNotificationCommand): Shipment.ShipmentId

    data class ShipmentNotificationCommand(
        val merchantId: UUID,
        val orderId: Order.OrderId,
        val totalShipped: Double,
        val items: List<UUID>?) {}
}