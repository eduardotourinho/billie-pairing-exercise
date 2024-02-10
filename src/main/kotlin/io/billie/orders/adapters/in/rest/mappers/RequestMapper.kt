package io.billie.orders.adapters.`in`.rest.mappers

import io.billie.orders.adapters.`in`.rest.models.OrderRequest
import io.billie.orders.adapters.`in`.rest.models.ShipmentRequest
import io.billie.orders.app.ports.`in`.commands.NewOrderCommand
import io.billie.orders.app.ports.`in`.commands.ShipmentNotificationCommand
import org.springframework.stereotype.Component
import java.util.*

@Component
class RequestMapper {

    fun mapOrderRequestToCommand(
        orderRequest: OrderRequest
    ): NewOrderCommand {
        val items = orderRequest.items
            .map { itemRequest -> itemRequest.id }
            .toList()

        return  NewOrderCommand(orderRequest.merchantId, items, orderRequest.total)
    }

    fun mapShipmentRequestToCommand(
        orderId: UUID,
        shipmentRequest: ShipmentRequest
    ): ShipmentNotificationCommand {
        val items = shipmentRequest.items
            .map { itemRequest -> itemRequest.id }
            .toList()

        return ShipmentNotificationCommand(shipmentRequest.merchantId, orderId, items)
    }
}