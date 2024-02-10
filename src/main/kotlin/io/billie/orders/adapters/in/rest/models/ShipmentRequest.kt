package io.billie.orders.adapters.`in`.rest.models

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID
import javax.validation.constraints.NotEmpty

data class ShipmentRequest(
    @JsonProperty("merchant_id") val merchantId: UUID,
    @NotEmpty val items: List<ItemRequest>,
)
