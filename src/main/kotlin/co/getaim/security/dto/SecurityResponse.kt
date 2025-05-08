package co.getaim.security.dto

import co.getaim.security.entity.Security

data class SecurityResponse(
    val name: String,
    val ticker: String,
    val price: Int,
) {
    companion object {
        fun from(security: Security): SecurityResponse =
            SecurityResponse(
                name = security.name,
                ticker = security.ticker,
                price = security.price,
            )
    }
}
