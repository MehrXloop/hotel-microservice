package com.mock.demo;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mock.demo.controller.HotelController;
import com.mock.demo.modal.Hotel;
import com.mock.demo.repository.HotelRepository;

@AutoConfigureJsonTesters
@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

	private MockMvc mvc;
	@Mock
	private HotelRepository hotelRepository;

	@InjectMocks
	private HotelController hotelController;

	private JacksonTester<Hotel> jsonHotel;

	private JacksonTester<Collection<Hotel>> jsonHotels;

	@BeforeEach
	public void setUp() {
		JacksonTester.initFields(this, new ObjectMapper());
		mvc = MockMvcBuilders.standaloneSetup(hotelController).build();
	}

	// can post a hotel
	@Test
	public void canAddANewHotel() throws Exception {
		Hotel hotel = new Hotel(1L, "Hotel Swiss", "It is a short desc", "It is a long desc", "http:image.com",
				"Karachi", "Business", false, 123.00);
		when(hotelRepository.save(hotel)).thenReturn((hotel));
		mvc.perform(MockMvcRequestBuilders
				.post("/hotels/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonHotel.write(hotel).getJson()))
				.andExpect(MockMvcResultMatchers.status().isOk());

	}

	// can get all hotels

	@Test
	public void canGetAllHotels() throws Exception {
		Hotel hotel1 = new Hotel(1L, "Hotel Swiss", "It is a short desc", "It is a long desc", "http:image.com",
				"Karachi", "Business", false, 123.00);
		Hotel hotel2 = new Hotel(2L, "Hotel Swiss", "It is a short desc", "It is a long desc", "http:image.com",
				"Karachi", "Business", false, 123.00);
		List<Hotel> listOfHotels = new ArrayList<>();
		listOfHotels.add(hotel1);
		listOfHotels.add(hotel2);

		when(hotelRepository.findAll()).thenReturn(listOfHotels);

		mvc.perform(MockMvcRequestBuilders
				.get("/hotels/all")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(
						MockMvcResultMatchers.content().json(jsonHotels.write(listOfHotels).getJson()));
	}

	// can get one hotel by hotel id

	@Test
	public void canGetAProduct() throws Exception {
		Hotel hotel = new Hotel(1L, "Hotel Swiss", "It is a short desc", "It is a long desc", "http:image.com",
				"Karachi", "Business", false, 123.00);
		when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
		mvc.perform(MockMvcRequestBuilders.get("/hotels/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(jsonHotel.write(hotel).getJson()));
	}

}
