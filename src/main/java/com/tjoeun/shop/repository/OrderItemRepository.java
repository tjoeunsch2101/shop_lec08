package com.tjoeun.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tjoeun.shop.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
