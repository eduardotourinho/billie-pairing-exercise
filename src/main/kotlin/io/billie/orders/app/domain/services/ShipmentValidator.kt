package io.billie.orders.app.domain.services

import io.billie.orders.app.domain.entities.Shipment
import io.billie.orders.app.domain.services.validators.ItemInOrderValidator
import io.billie.orders.app.domain.services.validators.ItemIsShippableValidator
import org.springframework.stereotype.Service

@Service
class ShipmentValidator(
    val itemInOrderValidator: ItemInOrderValidator,
    val itemIsShippableValidator: ItemIsShippableValidator
) {

    fun isValid(shipment: Shipment): Boolean {
        return true
    }
}