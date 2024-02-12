package io.billie.orders.adapters.`in`.rest.controllers

import io.billie.orders.adapters.`in`.rest.mappers.RequestMapper
import io.billie.orders.adapters.`in`.rest.models.OrderRequest
import io.billie.orders.app.domain.entities.Order
import io.billie.orders.app.ports.`in`.CreateOrderUseCase
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/billie/orders")
class OrderController(
    val createOrderUseCase: CreateOrderUseCase,
    val requestMapper: RequestMapper
) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Merchant's order created",
                content = [Content(schema = Schema(implementation = Order::class))]
            ),
            ApiResponse(responseCode = "400", description = "Bad request", content = [Content()]),
            ApiResponse(responseCode = "500", description = "Internal server error", content = [Content()])
        ]
    )
    fun createOrder(@Validated @RequestBody orderRequest: OrderRequest): Order {
        return createOrderUseCase.createOrder(requestMapper.mapOrderRequestToCommand(orderRequest))
    }
}
