package co.com.bancolombia.reactivemq.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class Route {

    private static final  String ROUTE_PUT_MESSAGE = "/putmessage/{message}";
    private static final  String ROUTE_GET_MESSAGE = "/getmessage";

    @Bean
    RouterFunction<ServerResponse> routes(Handler handler){
        return route(GET(ROUTE_PUT_MESSAGE).and(accept(MediaType.APPLICATION_JSON)), handler::putMessage)
                .andRoute(GET(ROUTE_GET_MESSAGE).and(accept(MediaType.APPLICATION_JSON)), handler::getMessage);
    }


}
