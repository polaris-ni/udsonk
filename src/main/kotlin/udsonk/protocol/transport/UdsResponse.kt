package com.lyni.udsonk.protocol.transport

sealed interface UdsResponse {
    data object Pending : UdsResponse
    data class Success(val data: ByteArray) : UdsResponse {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Success

            return data.contentEquals(other.data)
        }

        override fun hashCode(): Int {
            return data.contentHashCode()
        }
    }

    data class Failure(val nrc: Byte) : UdsResponse
}