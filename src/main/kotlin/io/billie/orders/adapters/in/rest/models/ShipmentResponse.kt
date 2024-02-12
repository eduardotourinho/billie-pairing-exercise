package io.billie.orders.adapters.`in`.rest.models

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class ShipmentResponse(@JsonProperty("tracking_code") val trackingId: UUID)
