package cgg.informatique.jfl.webSocket;

import cgg.informatique.jfl.webSocket.configurations.MonStompSessionHandler;
import cgg.informatique.jfl.webSocket.configurations.WebSocketConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class WebSocketApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(WebSocketApplication.class, args);
	}
	@Autowired
	private WebSocketConfig webSocket;

	@Override
	public void run(String... args) throws Exception {
// Ce laboratoire est basé sur les exemples suivants:
// https://spring.io/guides/gs/messaging-stomp-websocket/
// https://www.sitepoint.com/implementing-spring-websocket-server-and-client/
// https://www.baeldung.com/websockets-api-java-spring-client
// https://www.baeldung.com/spring-security-websockets
// https://www.html5rocks.com/en/tutorials/getusermedia/intro/
// https://davidwalsh.name/demo/camera.php
// https://spring.io/blog/2015/06/08/cors-support-in-spring-framework
// https://developer.mozilla.org/fr/docs/Web/API/MediaDevices/getUserMedia

		String monsieurPatate = new String("data:image/jpeg;base64,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");
		//
		WebSocketClient simpleWebSocketClient = new StandardWebSocketClient();
		List<Transport> transports = new ArrayList<>();


		transports.add(new WebSocketTransport(simpleWebSocketClient));
		SockJsClient sockJsClient = new SockJsClient(transports);

		//Créer un client Stomp qui gère les dojo.
		WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
		MappingJackson2MessageConverter conversionJson = new MappingJackson2MessageConverter();
		stompClient.setMessageConverter(conversionJson);
		stompClient.setInboundMessageSizeLimit(32 * 1024);

		//Se connecter à un websocket
		String url = "ws://localhost:8098/webSocket";
		StompSessionHandler sessionHandler = new MonStompSessionHandler();
		StompSession session = stompClient.connect(url, sessionHandler).get();

		//S'abonner à la file de message /sujet/reponse
		session.subscribe("/sujet/reponse", new StompFrameHandler() {
			@Override
			public Type getPayloadType(StompHeaders headers) {
				return Reponse.class;
			}

			@Override
			public void handleFrame(StompHeaders headers,Object payload) {
				System.err.println(payload.toString());
			}
		});

		/*while(true) {
			//À toutes les 5 secondes, un message est transmis par le serveur.
			Thread.sleep(5000);
			Long creation  = System.currentTimeMillis();

			Message unMessage = new Message("Serveur" , "hello",  creation, monsieurPatate );
			while (! session.isConnected()) {
				session = stompClient.connect(url, sessionHandler).get();
				System.out.println("reconnexion session...");
			}
			System.out.println("session ok.");
			session.send("/app/message", unMessage);

		}*/

	}

}
