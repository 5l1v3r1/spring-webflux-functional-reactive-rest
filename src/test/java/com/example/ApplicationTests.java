package com.example;

import com.example.model.Hotel;
import com.example.repository.HotelRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	HotelRepository hotelRepository;

	@Test
	public void contextLoads() {

		Hotel hotel = new Hotel("This is a Test Hotel");

		webTestClient.post().uri("/hotels")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(hotel), Hotel.class)
				.exchange()
				.expectStatus().isCreated()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody()
				.jsonPath("$.id").isNotEmpty()
				.jsonPath("$.name").isEqualTo("This is a Test Hotel");
	}

	@Test
	public void testGetAllHotels() {
		webTestClient.get().uri("/hotels")
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBodyList(Hotel.class);
	}

	@Test
	public void testDeleteHotel() {
		Hotel hotel = hotelRepository.save(new Hotel("To be deleted")).block();

		webTestClient.delete()
				.uri("/hotels/{id}", Collections.singletonMap("id",  hotel.getId()))
				.exchange()
				.expectStatus().isOk();
	}
}
