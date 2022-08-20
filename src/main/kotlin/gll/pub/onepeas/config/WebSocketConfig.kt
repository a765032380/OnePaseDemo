package gll.pub.onepeas.config

import gll.pub.onepeas.eventHandlers.SocketHandler
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.*

@Configuration @EnableWebSocket
class WebSocketConfig : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(SocketHandler(), "/")
            .setAllowedOrigins("*")
            .withSockJS()
    }
}