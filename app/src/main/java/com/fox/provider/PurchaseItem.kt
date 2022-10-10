package com.fox.provider

data class PurchaseItem(
    var id: Int,
    val name: String,
    val count: Int,
    val enabled: Boolean
)