package com.lyni.udsonk.protocol.transport.impl

import com.lyni.udsonk.protocol.exceptions.ConnectionException
import com.lyni.udsonk.protocol.transport.TransportInterface
import java.io.Closeable
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketTimeoutException
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Socket-based UDS transport implementation
 *
 * @property config Transport configuration parameters
 */
class SocketTransport(
    private val config: TransportConfig = TransportConfig()
) : TransportInterface {
    private var socket: Socket? = null
    private var inputStream: InputStream? = null
    private var outputStream: OutputStream? = null

    @Volatile
    private var connected: Boolean = false

    /**
     * Transport layer configuration
     */
    data class TransportConfig(
        val host: String = "localhost",
        val port: Int = 13400,
        val connectTimeout: Duration = 5.seconds,
        val readTimeout: Duration = 30.seconds,
        val bufferSize: Int = 4096,
        val retryPolicy: RetryPolicy = RetryPolicy(maxAttempts = 3)
    )

    /**
     * Retry policy configuration
     */
    data class RetryPolicy(
        val maxAttempts: Int = 3,
        val backoffBase: Duration = 1.seconds
    )

    override fun connect() {
        if (connected) return

        withRetry(config.retryPolicy) { attempt ->
            try {
                val newSocket = Socket().apply {
                    soTimeout = config.readTimeout.inWholeMilliseconds.toInt()
                    connect(
                        InetSocketAddress(config.host, config.port),
                        config.connectTimeout.inWholeMilliseconds.toInt()
                    )
                }

                synchronized(this) {
                    socket = newSocket
                    inputStream = newSocket.getInputStream().buffered(config.bufferSize)
                    outputStream = newSocket.getOutputStream()
                    connected = true
                }
                true
            } catch (e: SocketTimeoutException) {
                throw ConnectionException("Connection timeout [${config.host}:${config.port}]", e)
            } catch (e: IOException) {
                if (attempt == config.retryPolicy.maxAttempts) {
                    throw ConnectionException("Connection failed [Attempt $attempt]: ${e.message}", e)
                }
                false
            }
        }
    }

    override fun disconnect() {
        synchronized(this) {
            connected = false
            inputStream?.closeQuietly()
            outputStream?.closeQuietly()
            socket?.closeQuietly()
            socket = null
        }
    }

    override fun send(data: ByteArray, offset: Int, size: Int) {
        checkConnection()

        try {
            synchronized(this) {
                outputStream!!.write(data, offset, size)
                outputStream!!.flush()
            }
        } catch (e: IOException) {
            throw ConnectionException("Data transmission failed", e)
        }
    }

    override fun receive(): ByteArray {
        checkConnection()

        return try {
            val buffer = ByteArray(config.bufferSize)
            when (val bytesRead = inputStream!!.read(buffer)) {
                -1 -> throw ConnectionException("Connection closed")
                0 -> ByteArray(0)
                else -> buffer.copyOf(bytesRead)
            }
        } catch (e: IOException) {
            throw ConnectionException("Data reception failed", e)
        }
    }

    override fun isConnected(): Boolean = connected

    override fun close() {
        disconnect()
    }

    private fun checkConnection() {
        if (!connected) {
            throw ConnectionException("Connection not established")
        }
    }

    private inline fun withRetry(policy: RetryPolicy, block: (attempt: Int) -> Boolean) {
        var currentAttempt = 1
        while (currentAttempt <= policy.maxAttempts) {
            if (block(currentAttempt)) return
            Thread.sleep(policy.backoffBase.inWholeMilliseconds * currentAttempt)
            currentAttempt++
        }
    }

    private fun Closeable.closeQuietly() {
        try {
            close()
        } catch (e: Exception) {
            // Ignore closure exceptions
        }
    }
}