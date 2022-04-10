package com.maersk.containers.entity;

import com.maersk.containers.enums.ContainerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Table("bookings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bookings {
  @PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
  private UUID id=UUID.randomUUID();
  @Column("container_size")
  private Integer containerSize;
  @Column("container_type")
  private ContainerType containerType;
  @Column("origin")
  private String origin;
  @Column("destination")
  private String destination;
  @Column("quantity")
  private Integer quantity;
  @Column("timestamp")
  private String timestamp= Instant.now().toString();
}
