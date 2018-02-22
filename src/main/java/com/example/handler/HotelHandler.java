package com.example.handler;

import com.example.model.Hotel;
import com.example.repository.HotelRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;


@Component
public class HotelHandler {

    private HotelRepository hotelRepository;

    public HotelHandler(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    /**
     * get All Hotel
     * @return
     */
    public Mono<ServerResponse> getAll(ServerRequest request) {
        Flux<Hotel> hotels = hotelRepository.findAll();

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(hotels, Hotel.class);
    }

    /**
     * get Hotel by id
     * @param request
     * @return
     */
    public Mono<ServerResponse> getHotel(ServerRequest request) {
        String hotelId = request.pathVariable("id");

        Mono<ServerResponse> notFond = ServerResponse.notFound().build();

        Mono<Hotel> hotelMono = hotelRepository.findById(hotelId);

        return hotelMono.flatMap(hotel -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
        .body(fromObject(hotel)))
                .switchIfEmpty(notFond);
    }

    /**
     * delete a hotel
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> deleteHotel(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");

        return ServerResponse.ok().build(hotelRepository.deleteById(id));
    }

    /**
     * create a Hotel
     * @param request
     * @return
     */
    public Mono<ServerResponse> createHotel(ServerRequest request) {
        Mono<Hotel> hotelSaved = request.bodyToMono(Hotel.class);
        Mono<Hotel> hotelMonoSaved = hotelSaved.flatMap(hotelRepository::save);

        return ServerResponse.status(HttpStatus.CREATED).body(hotelMonoSaved, Hotel.class);
    }

    /**
     * Update a Hotel
     * @param request
     * @return
     */
    public Mono<ServerResponse> putHotel(ServerRequest request) {

        String id = request.pathVariable("id");

        Mono<Hotel> hotelRequestMono = request.bodyToMono(Hotel.class);

        Hotel hotelUpdate = hotelRequestMono.block();

        Mono<Hotel> hotelUpdateMono = this.hotelRepository.findById(id)
                .map(hotel -> {
                    hotel.setName(hotelUpdate.getName());
                    hotel.setAddress(hotelUpdate.getAddress());
                    hotel.setCreatedAt(hotelUpdate.getCreatedAt());

                    return hotel;
                })
                .flatMap(hotel -> this.hotelRepository.save(hotel));

        return ServerResponse.status(HttpStatus.OK).body(hotelUpdateMono, Hotel.class);
    }
}
