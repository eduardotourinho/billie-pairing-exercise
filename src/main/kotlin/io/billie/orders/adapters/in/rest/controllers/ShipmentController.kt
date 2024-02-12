package io.billie.orders.adapters.`in`.rest.controllers

import io.billie.orders.adapters.`in`.rest.mappers.RequestMapper
import io.billie.orders.adapters.`in`.rest.mappers.ResponseMapper
import io.billie.orders.adapters.`in`.rest.models.ErrorResponse
import io.billie.orders.adapters.`in`.rest.models.ShipmentRequest
import io.billie.orders.adapters.`in`.rest.models.ShipmentResponse
import io.billie.orders.app.ports.`in`.NotifyOrderShipmentUseCase
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/billie/shipments")
class ShipmentController(
    private val notifyShipmentUseCase: NotifyOrderShipmentUseCase,
    private val requestMapper: RequestMapper,
    private val responseMapper: ResponseMapper
) {

    @PostMapping("/{orderId}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Merchant's shipment was notified",
                content = [Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = ShipmentResponse::class)
                )]
            ),
            ApiResponse(
                responseCode = "400", description = "Bad request", content = [Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = ErrorResponse::class)
                )]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal server error", content = [Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = ErrorResponse::class)
                )]
            )
        ]
    )
    fun createShipment(
        @PathVariable("orderId") orderId: UUID,
        @Validated @RequestBody shipmentRequest: ShipmentRequest
    ): ShipmentResponse {
        val shipmentId =
            notifyShipmentUseCase.notifyShipment(requestMapper.mapShipmentRequestToCommand(orderId, shipmentRequest))

        return responseMapper.shipmentIdToResponse(shipmentId)
    }
}