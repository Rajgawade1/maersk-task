package com.maersk.containers.controller;

import com.maersk.containers.dto.ContainerSpecs;
import com.maersk.containers.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/bookings")
public class ContainerController {

  @Autowired
  ContainerService containerService;

  @PostMapping("/cont-availability")
  public Mono<String> contAvailability(@Valid @RequestBody final ContainerSpecs requestParams){
    return Mono.just(requestParams).flatMap( data-> containerService.checkAvailability(data));
  }
  @PostMapping("/container")
  public Mono<String> container(@Valid @RequestBody final ContainerSpecs requestParams){
    return Mono.just(requestParams).flatMap(data-> {
     return containerService.saveBookings(data);
    });
  }

}
