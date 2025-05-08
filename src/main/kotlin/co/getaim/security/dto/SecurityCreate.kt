package co.getaim.security.dto

import co.getaim.security.entity.Security
import jakarta.validation.constraints.*

data class SecurityCreate(
    @field:Size(min = 1, max = 255, message = "Name must be between 1 and 255")
    val name: String,
    @field:Size(min = 1, max = 255, message = "Ticker must be between 1 and 255")
    val ticker: String,
    @field:Positive
    val price: Int,
) {
    fun toEntity() = Security(
        name = name,
        ticker = ticker,
        price = price,
    )
}
