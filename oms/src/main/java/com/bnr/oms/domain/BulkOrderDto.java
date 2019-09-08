package com.bnr.oms.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class BulkOrderDto {

  @Valid
  @JsonValue
  @NotEmpty
  private List<OrderDto> orders;

}
