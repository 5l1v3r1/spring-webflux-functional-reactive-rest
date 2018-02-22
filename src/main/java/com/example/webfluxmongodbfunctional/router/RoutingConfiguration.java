package com.example.webfluxmongodbfunctional.router;

import com.example.webfluxmongodbfunctional.handler.HotelHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RoutingConfiguration {

    @Bean
    public RouterFunction<ServerResponse> monoRouteFunction(HotelHandler hotelHandler){
        return route(GET("/hotels"), hotelHandler::getAll)
                .andRoute(GET("/hotels/{id}"), hotelHandler::getHotel)
                .andRoute(POST("/hotels").and(accept(MediaType.APPLICATION_JSON)), hotelHandler::createHotel)
                .andRoute(DELETE("/hotels/{id}"), hotelHandler::deleteHotel)
                .andRoute(PUT("/hotels/{id}").and(accept(MediaType.APPLICATION_JSON)), hotelHandler::putHotel);
    }
}
