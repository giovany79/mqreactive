package co.com.bancolombia.reactivemq.controller;

import co.com.bancolombia.reactivemq.service.SendMessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.jms.JMSException;

@AllArgsConstructor
@Component
public class Handler {

    SendMessageService service;

    public Mono<ServerResponse> putMessage(ServerRequest request){
        try {
            String message = request.pathVariable("message");
            Mono<String> response = service.send(message);
            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response, String.class);
        }catch(JMSException e){

            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just( e.getLinkedException()), String.class);
        }
    }

    public Mono<ServerResponse> getMessage(ServerRequest request){
        try {
            Mono<String> response = service.recv();
            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response, String.class);
        }catch(JMSException e){

            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just( e.getLinkedException()), String.class);
        }
    }
}
