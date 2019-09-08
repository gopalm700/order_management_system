package com.bnr.notification.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class QueueMessage(
        var orderId: String,
        var deliveryTime: String?,
        var action: String,
        var quantity: Int?,
        var callback: String?
)
