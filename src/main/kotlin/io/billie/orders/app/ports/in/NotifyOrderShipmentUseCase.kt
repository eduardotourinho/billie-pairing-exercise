package io.billie.orders.app.ports.`in`

import io.billie.orders.app.ports.`in`.commands.ShipmentNotificationCommand

interface NotifyOrderShipmentUseCase {

    fun notifyShipment(command: ShipmentNotificationCommand)
}