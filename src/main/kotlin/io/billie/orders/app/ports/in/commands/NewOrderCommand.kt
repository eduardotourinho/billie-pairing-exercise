package io.billie.orders.app.ports.`in`.commands

import java.util.UUID

data class NewOrderCommand(val merchantId: UUID, val items: List<UUID>, val total: Double)
