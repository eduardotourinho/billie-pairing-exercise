package io.billie.orders.app.ports.`in`.commands

import java.util.UUID

data class ShipmentNotificationCommand(val merchantId: UUID, val orderId: UUID, val items: List<UUID>)
