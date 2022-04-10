package com.maersk.containers.service;

import com.maersk.containers.entity.Bookings;
import com.maersk.containers.repository.BookingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BookingsService {
  @Autowired
  BookingsRepository bookingsRepository;

  public Mono<Object> saveBooking(Bookings bookings){
    return bookingsRepository.save(bookings).map(data->{
      return data;
    });
  }
}
