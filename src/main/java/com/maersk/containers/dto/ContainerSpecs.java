package com.maersk.containers.dto;

import com.maersk.containers.enums.ContainerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContainerSpecs {
  @Min(1) @Max(100)
  private Integer containerSize;
  private ContainerType containerType;
  @Size(min = 5, max = 20)
  private String origin;
  @Size(min = 5, max = 20)
  private String destination;
  @Min(value = 1,message = "Minimum 1 quality is required") @Max(value = 100,message = "Maximum 100 quality is allowed")
  private Integer quantity;
  private String timestamp;
  private String bookingRef;
}
