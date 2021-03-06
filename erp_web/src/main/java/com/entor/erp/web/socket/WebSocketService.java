package com.entor.erp.web.socket;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

@Component
@ServerEndpoint("/websocket/{uuid}")
@Slf4j
public class WebSocketService {
	
	private Session session;

	private static final CopyOnWriteArraySet<WebSocketService> WEB_SOCKET_SERVICES  = new CopyOnWriteArraySet<>();
	
	private static final ConcurrentHashMap<String, WebSocketService> USER_MAP = new ConcurrentHashMap<>();
	
	@OnOpen
	public void onOpen(Session session,@PathParam("uuid")String uuid) {
		this.session = session;
		WEB_SOCKET_SERVICES.add(this);
		USER_MAP.put(uuid, this);
		log.debug(String.format("WebSocket新连接，总数：%s", WEB_SOCKET_SERVICES.size()));
	}
	
	@OnClose
	public void onClose(Session session,@PathParam("uuid")String uuid) {
		System.out.println("离开Id:"+uuid);
		WEB_SOCKET_SERVICES.remove(this);
		USER_MAP.remove(uuid);
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
	
	public void sendByUserid(Object message,Long empid) {
		WebSocketService webSocketService = USER_MAP.get(empid.toString());
		if(webSocketService!=null)
			try {
				webSocketService.session.getBasicRemote().sendText(JSONObject.toJSONString(message));
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
}
