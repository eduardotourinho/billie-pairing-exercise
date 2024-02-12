package io.billie.orders.adapters.`in`.rest.mappers

import io.billie.orders.adapters.`in`.rest.models.OrderRequest
import io.billie.orders.adapters.`in`.rest.models.ShipmentRequest
import io.billie.orders.app.domain.entities.Order
import io.billie.orders.app.ports.`in`.CreateOrderUseCase
import io.billie.orders.app.ports.`in`.NotifyOrderShipmentUseCase

import org.springframework.stereotype.Component
import java.util.*

@Component
class RequestMapper {

    fun mapOrderRequestToCommand(
        orderRequest: OrderRequest
    ): CreateOrderUseCase.NewOrderCommand {
        val items = orderRequest.items
            .map { itemRequest -> itemRequest.id }
            .toList()

        return CreateOrderUseCase.NewOrderCommand(orderRequest.merchantId, items, orderRequest.total)
    }

    fun mapShipmentRequestToCommand(
        orderId: UUID,
        shipmentRequest: ShipmentRequest
    ): NotifyOrderShipmentUseCase.ShipmentNotificationCommand {
        var items = listOf<UUID>()
        if (shipmentRequest.items != null) {
            items = shipmentRequest.items!!
                .map { itemRequest -> itemRequest.id }
                .toList()
        }

        return NotifyOrderShipmentUseCase.ShipmentNotificationCommand(
            shipmentRequest.merchantId,
            Order.OrderId(orderId),
            shipmentRequest.totalShipped,
            items
        )
    }
}