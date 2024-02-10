package io.billie.orders.adapters.out.storage.service

import io.billie.orders.app.domain.entities.Shipment
import io.billie.orders.app.ports.out.ShipmentNotifierPort
import org.springframework.stereotype.Service

@Service
class ShipmentNotifierService: ShipmentNotifierPort {

    override fun notifyShipment(shipment: Shipment) {
        TODO("Not yet implemented")
    }
}