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
public class CartItem extends BasicEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cart_item_id", nullable=false)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	// 외래키 Annotation  <-- @JoinColumn
	@JoinColumn(name="cart_id")	
	private Cart cart;
	
	@ManyToOne(fetch=FetchType.LAZY)
  // 외래키 Annotation  <-- @JoinColumn
	@JoinColumn(name="item_id")	
	private Item item;
	
	
	private int count;
	
	
}









