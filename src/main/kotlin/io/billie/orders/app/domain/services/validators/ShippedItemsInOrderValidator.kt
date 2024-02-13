package io.billie.orders.app.domain.services.validators

import io.billie.orders.app.domain.entities.Shipment
import org.springframework.stereotype.Component

@Component
class ShippedItemsInOrderValidator {

    fun isValid(shipment: Shipment): Boolean {
        if (shipment.shippedItems.isNullOrEmpty()) {
            return true
        }

        return shipment.shippedItems.isNotEmpty() && shipment.order!!.items.containsAll(
            shipment.shippedItems
        )
    }
}
