package gll.pub.onepeas.eventHandlers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.web.socket.*
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.atomic.AtomicLong

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class SocketUser(val id: Long, val name: String)
class Message(val msgType: String, val data: Any)

class SocketHandler : TextWebSocketHandler() {

    private val log: Logger = LoggerFactory.getLogger(SocketHandler::class.java)
    private val sessionList = HashMap<WebSocketSession, SocketUser>()
    private var uids = AtomicLong(0)

    @Throws(Exception::class)
    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessionList -= session
    }
    //{"type":"join","data":"gll"}
    //{"type":"say","data":"gll"}

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        try {
            val json = ObjectMapper().readTree(message?.payload)

            when (json.get("type").asText()){
                "join" -> {
                    log.info("Received `join` request...")
                    val user = SocketUser(uids.getAndIncrement(), json.get("data").asText())
                    sessionList.put(session!!, user)
                    emit(session, Message("users", sessionList.values))
                    broadcastToOthers(session, Message("join", user))
                }
                "say" -> {
                    log.info("Received `say` request...")
                    log.info("data: ${json.get("data").asText()}")
                    broadcastToOthers(session, Message("message", json.get("data").asText()))
                }
            }

        } catch(e: Exception){
            e.printStackTrace()
        }
    }

    fun emit(session: WebSocketSession, msg: Message) = session.sendMessage(TextMessage(jacksonObjectMapper().writeValueAsString(msg)))
    fun broadcast(msg: Message) = sessionList.forEach { emit(it.key, msg) }
    fun broadcastToOthers(me: WebSocketSession, msg: Message) = sessionList.filterNot { it.key == me }.forEach { emit(it.key, msg) }

}