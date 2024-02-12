package io.billie.orders.adapters.`in`.rest.models

import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorResponse(
    val status: Int?,
    @JsonProperty("error_message") val errorMessage: String?
)
