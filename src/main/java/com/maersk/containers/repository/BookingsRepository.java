package com.maersk.containers.repository;

import com.maersk.containers.entity.Bookings;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingsRepository extends ReactiveCassandraRepository<Bookings, String> {

}
