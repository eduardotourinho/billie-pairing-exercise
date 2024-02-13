package io.billie.orders.app.domain.exceptions

class ShipmentNotificationException(message: String?, exception: Throwable): RuntimeException(message, exception) {
}