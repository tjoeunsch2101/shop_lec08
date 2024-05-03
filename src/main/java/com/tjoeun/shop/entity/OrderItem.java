package com.tjoeun.shop.entity;

import com.tjoeun.shop.audit.BasicEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem extends BasicEntity{
	
	/*
	id  :  주문 상품 코드

	order_id : 주문 코드

	item_id  : 상품 코드

	orderPrice : 주문 가격

	count : 주문 수량
	*/
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="order_item_id")
	private Long id;

	// 외래키(Many)가 있는 자식 table 이 연관관계에서 주인이 됨
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="order_id")
	private Order order;
	
  // 외래키(Many)가 있는 자식 table 이 연관관계에서 주인이 됨
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="item_id")
	private Item item;
	
	private int orderPrice;
	
	private int count;
	
	
}





