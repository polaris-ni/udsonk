package com.lyni.udsonk.protocol.uds.services.standard.did.provider

import com.lyni.udsonk.application.client.UdsClientContext
import com.lyni.udsonk.protocol.exceptions.UdsFileOperationException
import com.lyni.udsonk.protocol.uds.services.standard.did.UdsDataIdentifier
import java.io.File

class UdsDataIdentifierFileProvider(val path: String) : UdsDataIdentifierProvider {
    override fun provide(context: UdsClientContext, did: UdsDataIdentifier): ByteArray {
        val file = File(path)
        if (!file.exists()) {
            throw UdsFileOperationException("$path not exist")
        }
        if (!file.isFile) {
            throw UdsFileOperationException("$path not a file")
        }
        if (!file.canRead()) {
            throw UdsFileOperationException("$path not readable")
        }
        val fileSize = file.length().toInt()
        val buffer = ByteArray(fileSize)
        file.inputStream().use { inputStream ->
            val readSize = inputStream.read(buffer)
            if (readSize != fileSize) {
                throw UdsFileOperationException("read file $path error, $readSize bytes read, but file size is $fileSize bytes")
            }
        }
        return buffer
    }
}