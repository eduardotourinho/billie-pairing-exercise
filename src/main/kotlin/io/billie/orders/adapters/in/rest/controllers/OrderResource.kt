package io.billie.orders.adapters.`in`.rest.controllers

import io.billie.orders.adapters.`in`.rest.mappers.RequestMapper
import io.billie.orders.adapters.`in`.rest.models.OrderRequest
import io.billie.orders.adapters.`in`.rest.models.ShipmentRequest
import io.billie.orders.app.ports.`in`.CreateOrderUseCase
import io.billie.orders.app.ports.`in`.NotifyOrderShipmentUseCase
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/billie/orders")
class OrderResource(
    val notifyShipmentUseCase: NotifyOrderShipmentUseCase,
    val createOrderUseCase: CreateOrderUseCase,
    val requestMapper: RequestMapper
) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Merchant's order created",
                content = [Content()]
            ),
            ApiResponse(responseCode = "400", description = "Bad request", content = [Content()]),
            ApiResponse(responseCode = "500", description = "Internal server error", content = [Content()])
        ]
    )
    fun createOrder(@Validated @RequestBody orderRequest: OrderRequest) {
        createOrderUseCase.createOrder(requestMapper.mapOrderRequestToCommand(orderRequest))
    }

    @PostMapping("/{orderId}/shipped-items", consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Merchant's shipment was notified",
                content = [Content()]
            ),
            ApiResponse(responseCode = "400", description = "Bad request", content = [Content()]),
            ApiResponse(responseCode = "500", description = "Internal server error", content = [Content()])
        ]
    )
    fun createShipment(
        @PathVariable("orderId") orderId: UUID,
        @Validated @RequestBody shipmentRequest: ShipmentRequest
    ) {
        notifyShipmentUseCase.notifyShipment(requestMapper.mapShipmentRequestToCommand(orderId, shipmentRequest))
    }


}
