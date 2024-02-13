package io.billie.orders.adapters.`in`.rest.models

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.validation.annotation.Validated
import java.util.UUID
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Validated
data class ShipmentRequest(
    @JsonProperty("merchant_id") @NotNull @NotBlank val merchantId: UUID,
    @JsonProperty("total_shipped") @Min(value = 0) val totalShipped: Double,
    @NotEmpty val items: List<ItemRequest>?,
)
