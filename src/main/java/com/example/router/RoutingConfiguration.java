package com.example.router;

import com.example.handler.HotelHandler;
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
                .andRoute(POST("/hotels").and(accept(MediaType.APPLICATION_JSON_UTF8)), hotelHandler::createHotel)
                .andRoute(DELETE("/hotels/{id}"), hotelHandler::deleteHotel)
                .andRoute(PUT("/hotels/{id}").and(accept(MediaType.APPLICATION_JSON_UTF8)), hotelHandler::putHotel);
    }
}
