package com.tjoeun.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tjoeun.shop.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

}
