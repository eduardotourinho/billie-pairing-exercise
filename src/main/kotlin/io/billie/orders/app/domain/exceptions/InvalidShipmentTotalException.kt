package io.billie.orders.app.domain.exceptions

import java.math.BigDecimal

class InvalidShipmentTotalException(
    shipmentTotal: BigDecimal,
    totalShipped: BigDecimal,
    orderTotal: BigDecimal
) :
    RuntimeException("Shipment of $shipmentTotal exceeds the sum of shipped items: $totalShipped. Order total: $orderTotal") {
}