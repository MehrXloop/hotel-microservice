package com.mock.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mock.demo.modal.Hotel;

public interface HotelRepository extends JpaRepository<Hotel,Long>{
    
}
