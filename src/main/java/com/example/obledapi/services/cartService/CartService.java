package com.example.obledapi.services.cartService;

import com.example.obledapi.Entities.Cart;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CartService {
    public ResponseEntity<?> createCart(String token, Cart cart);
    public ResponseEntity<?> getAllCarts(String token, Pageable pageable);
    public ResponseEntity<?> getOneCart(String token, Long cartId);
    public ResponseEntity<?> updateCart(String token, Cart cart, Long cartId);
    public ResponseEntity<?> deleteCart(String token, Long cartId);
}
