package com.example.obledapi.services.cartService;

import com.example.obledapi.Entities.Cart;
import com.example.obledapi.Entities.Users;
import com.example.obledapi.payloads.responses.DataResponse;
import com.example.obledapi.payloads.responses.MessageResponse;
import com.example.obledapi.repositories.CartRepository;
import com.example.obledapi.services.userService.UserService;
import com.example.obledapi.services.userService.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService = new UserServiceImpl();


    @Override
    public ResponseEntity<?> createCart(String token, Cart cart) {
        Optional<Users> organizer = userService.getUserByToken(token);
        if (organizer.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("You are not authenticate !"));
        }
        cartRepository.save(cart);
        return ResponseEntity.ok(new MessageResponse("Cart created !"));
    }

    @Override
    public ResponseEntity<?> getAllCarts(String token, Pageable pageable) {
        return ResponseEntity.ok(DataResponse.builder().data((List<?>) cartRepository.findAll(pageable)).build());
    }

    @Override
    public ResponseEntity<?> getOneCart(String token, Long cartId) {
        Optional<Users> organizer = userService.getUserByToken(token);
        if (organizer.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("You are not authenticate !"));
        }
        return ResponseEntity.ok(cartRepository.findById(cartId));
    }

    @Override
    public ResponseEntity<?> updateCart(String token, Cart cart, Long cartId) {
        Optional<Users> organizer = userService.getUserByToken(token);
        if (organizer.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("You are not authenticate !"));
        }
        Optional<Cart> cart1 = cartRepository.findById(cartId);
        if (organizer.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("This cart don't exist !"));
        }

        cart1.get().setName(cart.getName());
        cartRepository.save(cart1.get());
        return ResponseEntity.ok(new MessageResponse("Cart updated"));
    }

    @Override
    public ResponseEntity<?> deleteCart(String token, Long cartId) {
        Optional<Users> organizer = userService.getUserByToken(token);
        if (organizer.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("You are not authenticate !"));
        }
        cartRepository.deleteById(cartId);
        return ResponseEntity.ok(new MessageResponse("Cart deleted"));
    }
}
