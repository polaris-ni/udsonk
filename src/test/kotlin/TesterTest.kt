import com.lyni.udsonk.application.client.impl.DoSoAdUdsClient
import com.lyni.udsonk.common.util.NumberExtensions
import com.lyni.udsonk.protocol.transport.impl.SocketTransport
import com.lyni.udsonk.protocol.uds.UdsConstants
import com.lyni.udsonk.protocol.uds.services.impl.DiagnosticSessionControl
import com.lyni.udsonk.protocol.uds.session.UdsStandardSession
import org.tinylog.kotlin.Logger
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread
import kotlin.test.Test

@OptIn(ExperimentalStdlibApi::class)
class TesterTest {
    @Test
    fun testDiagnosticSessionControl() {
        thread {
            val port = 9999

            // 创建ServerSocket并绑定端口
            val serverSocket = ServerSocket(port)
            Logger.info("server start, listen port: $port")

            serverSocket.use { socket ->
                while (true) {
                    val clientSocket: Socket = socket.accept()
                    Logger.info("client connected: ${clientSocket.inetAddress.hostAddress}:${clientSocket.port}")
                    val buffer = ByteArray(1024)
                    val readSize = clientSocket.getInputStream().read(buffer)
                    val req = buffer.copyOf(readSize)
                    Logger.info("receive request: ${req.toHexString(NumberExtensions.UPPER_HEX_FORMAT)}")
                    val resp = byteArrayOf(
                        req[2], req[3], req[0], req[1], 0, 0, 0, 6,
                        (req[8] + UdsConstants.POSITIVE_RESP_OFFSET).toByte(), req[9], 0, 10, 0, 12
                    )
                    Logger.info("server reply: ${resp.toHexString(NumberExtensions.UPPER_HEX_FORMAT)}")
                    clientSocket.getOutputStream().write(resp)
                }
            }
        }

        DoSoAdUdsClient(0x1234, 0x5678, socketConfig = SocketTransport.TransportConfig()).use {
            it.start()
            it.sendRequest(DiagnosticSessionControl(UdsStandardSession.EXTENDED_DIAGNOSTIC_SESSION))
        }
    }
}