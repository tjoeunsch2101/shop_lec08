package com.tjoeun.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tjoeun.shop.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	

}
