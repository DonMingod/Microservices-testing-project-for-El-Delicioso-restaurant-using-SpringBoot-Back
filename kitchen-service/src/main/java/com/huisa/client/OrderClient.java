package com.huisa.client;

import com.huisa.dtos.orderclient.OrderResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "order-service",
        path = "/orders"
)
public interface OrderClient {

    @GetMapping("/{id}")
    OrderResponseDto getOrderById(@PathVariable("id") Long id,
                                  @RequestHeader("X-Auth-User-Id") String authUserId);

}
