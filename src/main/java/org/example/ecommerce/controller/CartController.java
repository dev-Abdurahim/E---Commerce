package org.example.ecommerce.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommerce.config.security.UserPrincipal;
import org.example.ecommerce.dto.request.CartRequest;
import org.example.ecommerce.dto.request.UpdateCartItemRequest;
import org.example.ecommerce.dto.response.CartResponse;
import org.example.ecommerce.service.temp.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/getMyCart")
    public ResponseEntity<CartResponse> getMyCart(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return ResponseEntity.ok(cartService.getMyCart(userPrincipal.getId()));
    }

    @PostMapping("/addItem")
    public ResponseEntity<CartResponse> addItem(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                @Valid @RequestBody CartRequest cartRequest){
        return ResponseEntity.ok(cartService.addItem(userPrincipal.getId(),cartRequest));
    }

    @PutMapping("/updateItem/{itemId}")
    public ResponseEntity<CartResponse> updateItem(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                   @PathVariable Long itemId,
                                                   @Valid @RequestBody UpdateCartItemRequest request){
        return ResponseEntity.ok(cartService.updateItem(userPrincipal.getId(), itemId,request));
    }

    @DeleteMapping("/removeItem/{itemId}")
    public ResponseEntity<CartResponse> removeItem(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                   @PathVariable Long itemId){
        return ResponseEntity.ok(cartService.removeItem(userPrincipal.getId(), itemId));
    }
}