package com.maersk.containers.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.maersk.containers.dto.ContainerSpecs;
import com.maersk.containers.entity.Bookings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ContainerService {
  @Autowired
  WebClient webClient;
  @Autowired
  BookingsService bookingsService;

  public Mono<String> checkAvailability(ContainerSpecs containerSpecs){
    return Mono.just(containerSpecs.toString()).flatMap(data-> getResponse(containerSpecs));
  }
  public Mono<String> saveBookings(ContainerSpecs containerSpecs){
    try {
      return Mono.just(containerSpecs).flatMap(data->{
        Bookings bookings = new Bookings();
        bookings.setContainerType(data.getContainerType());
        bookings.setContainerSize(data.getContainerSize());
        bookings.setOrigin(data.getOrigin());
        bookings.setDestination(data.getDestination());
        bookings.setQuantity(data.getQuantity());
        return bookingsService.saveBooking(bookings).map(res->{
          Bookings bookings1=(Bookings) res;
         return "{\"bookingRef\":\""+bookings1.getId().toString()+"\"}";
        });
      });
    }catch (Exception e){
      log.error("Exception occurred {}",e.getMessage());
      return Mono.just("HTTP 500 INTERNAL SERVER ERROR");
    }
  }
  Mono<String> getResponse(ContainerSpecs containerSpecs){
    return getMockClient(containerSpecs).flatMap(client->{
      return client.post().exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class))
              .map(data->{JsonObject jsonObject = new Gson().fromJson(data,JsonObject.class);
                if(jsonObject.get("availableSpace").getAsInt()==0){
                  return "{\"available\" : false}";
                }else{
                  return "{\"available\" : true}";
                }
              });
    });
  }
  Mono<WebClient> getMockClient(ContainerSpecs containerSpecs){
    webClient= WebClient.builder()
            .exchangeFunction(clientRequest ->{
              if(containerSpecs.getContainerSize()<=10){
                return Mono.just(ClientResponse.create(HttpStatus.OK)
                        .header("content-type", "application/json")
                        .body("{\"availableSpace\" : 6}")
                        .build());
              }else{
                return Mono.just(ClientResponse.create(HttpStatus.OK)
                        .header("content-type", "application/json")
                        .body("{\"availableSpace\" : 0}")
                        .build());
              }
              }

            ).build();
    return Mono.just(webClient);
  }
}
