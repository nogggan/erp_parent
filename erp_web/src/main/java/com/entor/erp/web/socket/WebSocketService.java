package com.entor.erp.web.socket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

@Component
@ServerEndpoint("/websocket")
@Slf4j
public class WebSocketService {
	
	private Session session;

	private static final CopyOnWriteArraySet<WebSocketService> WEB_SOCKET_SERVICES  = new CopyOnWriteArraySet<>();
	
	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		WEB_SOCKET_SERVICES.add(this);
		log.debug(String.format("WebSocket新连接，总数：%s", WEB_SOCKET_SERVICES.size()));
	}
	
	@OnClose
	public void onClose(Session session) {
		WEB_SOCKET_SERVICES.remove(this);
		log.debug(String.format("WebSocket断开连接，总数：%s", WEB_SOCKET_SERVICES.size()));
	}
	
	@OnMessage
	public void onMessage(Session session,String message) {
		log.debug(String.format("WebSocket接受到消息，消息：%s", message));
	}
	
	@OnError
	public void onError(Throwable e) {
		log.debug(String.format("WebSocket连接发生错误，消息：%s", e));
	}

	public void send(Object message) {
		WEB_SOCKET_SERVICES.forEach(x->{
			try {
				x.session.getBasicRemote().sendText(JSONObject.toJSONString(message));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
}
