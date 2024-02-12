package io.billie.orders.adapters.`in`.rest.controllers

import io.billie.orders.adapters.`in`.rest.models.ErrorResponse
import io.billie.orders.app.domain.exceptions.InvalidShipmentTotalException
import io.billie.orders.app.domain.exceptions.ShipmentNotificationException
import io.billie.orders.app.domain.exceptions.ShippedItemsNotInOrderException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ErrorControllerAdvice {

    @ExceptionHandler
    fun handleInvalidShipmentTotal(exception: InvalidShipmentTotalException): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.message)

        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleShippedItemsNotInOrder(exception: ShippedItemsNotInOrderException): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.message)

        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleHttpMessageNotReadableException(exception: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Invalid request body")

        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleShipmentNotificationException(exception: ShipmentNotificationException): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.message)

        return ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
