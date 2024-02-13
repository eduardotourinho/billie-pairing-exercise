package io.billie.orders.adapters.`in`.rest.models

import org.springframework.validation.annotation.Validated
import java.util.UUID

@Validated
data class ItemRequest(
   val id: UUID
)
