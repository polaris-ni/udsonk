package com.lyni.udsonk.protocol.uds.services.standard.did

import com.lyni.udsonk.application.client.UdsClientContext

/**
 * Uds data identifier provider
 */
interface UdsDataIdentifierProvider {
    /**
     * provide did data to be written
     *
     * @param context uds client context
     * @param did data identifier
     * @return [ByteArray] data to be written
     */
    fun provide(context: UdsClientContext, did: UdsDataIdentifier): ByteArray
}


