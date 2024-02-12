package io.billie.orders.adapters.`in`.rest.models

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class OrderRequest(

    @JsonProperty("merchant_id") @NotNull @NotBlank val merchantId: UUID,
    @NotEmpty val items: List<ItemRequest>,
    @Min(value = 0) val total: Double,
)
