package io.billie.orders.app.services

import io.billie.orders.app.domain.entities.Shipment
import io.billie.orders.app.domain.exceptions.InvalidShipmentTotalException
import io.billie.orders.app.domain.exceptions.ShipmentNotificationException
import io.billie.orders.app.domain.exceptions.ShippedItemsNotInOrderException
import io.billie.orders.app.domain.repositories.OrderRepository
import io.billie.orders.app.domain.services.ShipmentValidatorService
import io.billie.orders.app.domain.vo.Item
import io.billie.orders.app.ports.`in`.NotifyOrderShipmentUseCase
import io.billie.orders.app.ports.out.ShipmentNotifierPort
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class NotifyOrderShipmentService(
    private val shipmentValidator: ShipmentValidatorService,
    private val shipmentNotifierPort: ShipmentNotifierPort,
    private val orderRepository: OrderRepository
) : NotifyOrderShipmentUseCase {

    @Throws(
        ShippedItemsNotInOrderException::class,
        InvalidShipmentTotalException::class,
        ShipmentNotificationException::class
    )
    override fun notifyShipment(command: NotifyOrderShipmentUseCase.ShipmentNotificationCommand): Shipment.ShipmentId {

        val order = orderRepository.findByMerchantAndId(command.merchantId, command.orderId)

        val items = command.items?.map { item -> Item.create(item) }
        val shipment = Shipment.new(command.totalShipped, order, items)

        shipmentValidator.validate(shipment)
        return shipmentNotifierPort.notifyShipment(shipment)
    }
}
