package com.tjoeun.shop.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.tjoeun.shop.audit.BasicEntity;
import com.tjoeun.shop.constant.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
  @ToString(exclude={"member", "orderItemList"})
    ㄴ 연관관계로 설정된 멤버변수는 toString() 을 호출할 때
       무한 반복에 들어가므로 StackOverFlow 에러가 발생함
       이를 방지하고자 exclude 로 설정함  
  
*/
@Entity
@Table(name="orders")
@Setter @Getter 
@ToString(exclude={"member", "orderItemList"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends BasicEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="order_id")
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member;
	
	private LocalDateTime orderDate;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "order", 
			       cascade=CascadeType.ALL, orphanRemoval=true)
	private List<OrderItem> orderItemList = new ArrayList<>();
 
	
}
