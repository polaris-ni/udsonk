package com.lyni.udsonk.application.client

import com.lyni.udsonk.protocol.services.UdsServiceInterface

abstract class UdsClient(
    protected val context: UdsClientContext
) {
    abstract fun sendRequest(service: UdsServiceInterface)
}