package io.billie.orders.adapters.`in`.rest.mappers

import io.billie.orders.adapters.`in`.rest.models.ShipmentResponse
import io.billie.orders.app.domain.entities.Shipment
import org.springframework.stereotype.Component

@Component
class ResponseMapper {

    fun shipmentIdToResponse(shipmentId: Shipment.ShipmentId): ShipmentResponse {
        return ShipmentResponse(shipmentId.value)
    }
}