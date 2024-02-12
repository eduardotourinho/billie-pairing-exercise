package io.billie.orders.app.domain.services.validators

import io.billie.orders.app.domain.entities.Shipment
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class ShipmentTotalValidator {

    fun isValid(shipment: Shipment, previousShipments: List<Shipment>): Boolean {
        if (shipment.total == BigDecimal.ZERO || shipment.total == 0.0.toBigDecimal()) {
            return false
        }

        val sumShipments = shipment.total + previousShipments.sumOf { prevShipment -> prevShipment.total }
        return shipment.order!!.total - sumShipments >= BigDecimal.ZERO
    }
}
