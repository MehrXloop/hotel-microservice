package com.mock.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.mock.demo.modal.Hotel;
import com.mock.demo.repository.HotelRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/hotels")
public class HotelController {
    
    @Autowired
    private HotelRepository hotelRepo;

    //for posting hotel data
    @PostMapping("/add")
    public void addHotels(@RequestBody Hotel hotel){
        hotelRepo.save(hotel);
    }

    //get hotel list
    @GetMapping("/all")
    public List<Hotel> getListOfHotels(){
       return hotelRepo.findAll();
    }

    //get one hotel
    @GetMapping("/{id}")
    public Hotel getHotel(@PathVariable Long id){
      return  hotelRepo.findById(id).orElse(null);
    }
}
