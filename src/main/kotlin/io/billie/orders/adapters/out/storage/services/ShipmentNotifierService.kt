package io.billie.orders.adapters.out.storage.services

import io.billie.orders.app.domain.entities.Shipment
import io.billie.orders.app.domain.exceptions.ShipmentNotificationException
import io.billie.orders.app.domain.repositories.ShipmentRepository
import io.billie.orders.app.ports.out.ShipmentNotifierPort
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class ShipmentNotifierService(
    private val shipmentRepository: ShipmentRepository
) : ShipmentNotifierPort {

    @Throws(ShipmentNotificationException::class)
    override fun notifyShipment(shipment: Shipment): Shipment.ShipmentId {
        try {
            return shipmentRepository.insertShipment(shipment).id
        } catch (exception: Exception) {
            throw ShipmentNotificationException(exception.message, exception)
        }
    }
}
