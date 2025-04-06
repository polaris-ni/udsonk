package com.lyni.udsonk.application.client

data class UdsClientContext(
    val testerAddress: Short,
    var targetAddress: Short,
    var p2ServerMax: Short,
    var p2ServerMaxEnhanced: Short,
)