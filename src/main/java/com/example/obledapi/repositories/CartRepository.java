package com.example.obledapi.repositories;

import com.example.obledapi.Entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
