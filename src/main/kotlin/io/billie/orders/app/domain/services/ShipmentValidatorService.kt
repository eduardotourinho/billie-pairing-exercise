package io.billie.orders.app.domain.services

import io.billie.orders.app.domain.entities.Shipment
import io.billie.orders.app.domain.exceptions.InvalidShipmentTotalException
import io.billie.orders.app.domain.exceptions.ShippedItemsNotInOrderException
import io.billie.orders.app.domain.repositories.ShipmentRepository
import io.billie.orders.app.domain.services.validators.ShipmentTotalValidator
import io.billie.orders.app.domain.services.validators.ShippedItemsInOrderValidator
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class ShipmentValidatorService(
    private val shipmentTotalValidator: ShipmentTotalValidator,
    private val shippedItemsInOrderValidator: ShippedItemsInOrderValidator,
    private val shipmentRepository: ShipmentRepository
) {

    @Throws(ShippedItemsNotInOrderException::class, InvalidShipmentTotalException::class)
    fun validate(shipment: Shipment): Boolean {

        if (!shippedItemsInOrderValidator.isValid(shipment)) {
            throw ShippedItemsNotInOrderException(shipment.order?.items!!.map { it.id })
        }

        val previousShipments = shipmentRepository.findByOrder(shipment.order!!.id, shipment.order.merchantId)

        if (!shipmentTotalValidator.isValid(shipment, previousShipments)) {
            throw InvalidShipmentTotalException(
                shipment.total,
                previousShipments.sumOf { ps -> ps.total },
                shipment.order.total
            )
        }

        return true
    }
}
