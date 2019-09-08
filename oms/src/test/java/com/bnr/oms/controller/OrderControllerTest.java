package com.bnr.oms.controller;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bnr.oms.domain.OrderResponse;
import com.bnr.oms.events.OrderCreatedEvent;
import com.bnr.oms.workflow.OrchestrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private OrchestrationService service;

  @BeforeEach
  public void setUp() {
    reset(service);
  }

  @Nested
  class SingleOrderTest {

    @Test
    public void shouldCreateRecord() throws Exception {
      doNothing().when(service).orchestrate(any(OrderCreatedEvent.class));
      MvcResult result = mvc.perform(post("/api/orders")
          .contentType("application/json")
          .content(" {\"quantity\": 2,\"orderTime\": 1567962900000,\"orderId\":\"123\"}"))
          .andExpect(status().isCreated()).andReturn();
      assertThat(result.getResponse().getContentAsString())
          .isEqualToIgnoringWhitespace(
              new ObjectMapper().writeValueAsString(new OrderResponse("123")));
      verify(service).orchestrate(any(OrderCreatedEvent.class));
    }

    @Test
    public void shouldThrowBadRequestForInvalidQuantity() throws Exception {
      doNothing().when(service).orchestrate(any(OrderCreatedEvent.class));
      mvc.perform(post("/api/orders")
          .contentType("application/json")
          .content(" {\"quantity\": 0,\"orderTime\": 1567962900000,\"orderId\":\"123\"}"))
          .andExpect(status().isBadRequest());
    }
  }

  @Nested
  class BulkOrderTest {

    @Test
    public void shouldCreateRecords() throws Exception {
      doNothing().when(service).orchestrate(any(OrderCreatedEvent.class));
      MvcResult result = mvc.perform(post("/api/orders/bulk")
          .contentType("application/json")
          .content(
              " {\"orders\":[{\"quantity\": 2,\"orderTime\": 1567962900000,\"orderId\":\"123\"},"
                  + "{\"quantity\": 2,\"orderTime\": 1567962900000,\"orderId\":\"321\"}]}"))
          .andExpect(status().isCreated()).andReturn();
      assertThat(result.getResponse().getContentAsString())
          .isEqualToIgnoringWhitespace(
              new ObjectMapper().writeValueAsString(
                  Arrays.asList(new OrderResponse("123"), new OrderResponse("321"))));
      verify(service, times(2)).orchestrate(any(OrderCreatedEvent.class));
    }

    @Test
    public void shouldThrowBadRequestForInvalidQuantity() throws Exception {
      doNothing().when(service).orchestrate(any(OrderCreatedEvent.class));
      mvc.perform(post("/api/orders/bulk")
          .contentType("application/json")
          .content(" [{\"quantity\": 0,\"orderTime\": 1567962900000,\"orderId\":\"123\"}]"))
          .andExpect(status().isBadRequest());
    }
  }
}
