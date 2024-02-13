package io.billie.orders.app.domain.exceptions

import java.util.UUID

class ShippedItemsNotInOrderException(orderItems: List<UUID>) :
    RuntimeException("Some of the shipped items are not in the order. Order items: ${orderItems.joinToString { item -> item.toString() }} ") {
}