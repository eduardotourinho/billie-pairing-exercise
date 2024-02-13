package io.billie.orders.app.ports.out

import io.billie.orders.app.domain.entities.Shipment

interface ShipmentNotifierPort {

    fun notifyShipment(shipment: Shipment): Shipment.ShipmentId
}