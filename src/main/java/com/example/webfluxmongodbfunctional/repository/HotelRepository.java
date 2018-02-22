package com.example.webfluxmongodbfunctional.repository;

import com.example.webfluxmongodbfunctional.model.Hotel;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends ReactiveMongoRepository<Hotel, String> {

}
