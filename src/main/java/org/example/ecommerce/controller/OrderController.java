package org.example.ecommerce.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommerce.config.security.UserPrincipal;
import org.example.ecommerce.dto.request.CreateOrderRequest;
import org.example.ecommerce.dto.response.OrderResponse;
import org.example.ecommerce.service.temp.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping("/checout")
    public ResponseEntity<OrderResponse> checkout(@AuthenticationPrincipal UserPrincipal userPrincipal, @Valid @RequestBody CreateOrderRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.checkout(userPrincipal.getId(), request));
    }

    @GetMapping("/getMyOrders")
    public ResponseEntity<Page<OrderResponse>> getMyOrders(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                           @RequestParam(required = false, defaultValue = "0") int page,
                                                           @RequestParam(required = false, defaultValue = "10") int size){
        return ResponseEntity.ok(orderService.getMyOrders(userPrincipal.getId(), page,size));

    }

    @GetMapping("/getMyOrderById/{orderId}")
    public ResponseEntity<OrderResponse> getMyOrderById(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                        @PathVariable Long orderId){
        return ResponseEntity.ok(orderService.getMyOrderById(userPrincipal.getId(), orderId));
    }



}
