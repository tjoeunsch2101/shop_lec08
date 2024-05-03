package com.tjoeun.shop.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.tjoeun.shop.repository.ItemRepository;
import com.tjoeun.shop.repository.MemberRepository;
import com.tjoeun.shop.repository.OrderItemRepository;
import com.tjoeun.shop.repository.OrderRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
class OrderTest {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	public Item createItem() {
		Item item = Item.builder()
				            .itemName("스웨터")
				            .price(200000)
				            .stockNumber(50)
				            .itemDetail("스웨터 상세 설명")
				            //.regTime(LocalDateTime.now())
				            //.updateTime(LocalDateTime.now())
				            .build();
		return item;
	}
	


	@Test
	@DisplayName("cascade Test")
	public void cascadeTest() {
	  // Entity
		Order order = new Order();
		
		for(int i = 0; i < 3; i++) {
			// Entity
			Item item = this.createItem();
			// Entity 를 DB 에 저장함 <-- OrderItem 에 있는 item_id (외래키) 설정을 위함
			Item savedItem = itemRepository.save(item);
			
			OrderItem orderItem = OrderItem.builder()
					                           .order(order)
					                           .item(item)
					                           .orderPrice(2000000)
					                           .count(10)
					                           .build();
  		// Entity : DB 에 저장되기 전 
			order.getOrderItemList().add(orderItem);
		}
		
		log.info(">>>>>>>>>>>>>>>> order (Entity) : " + order);
		
		Order savedOrder = orderRepository.save(order);
		log.info(">>>>>>>>>>>>>>>> savedOrder : " + savedOrder);
		log.info(">>>>>>>>>>>>>>>> order (Entity) : " + order);
		
		Order foundOrder = orderRepository.findById(savedOrder.getId())
				                              .orElseThrow(EntityNotFoundException::new);
		log.info(">>>>>>>>>>>>>>>> foundOrder : " + foundOrder);
		
		assertEquals(3, savedOrder.getOrderItemList().size());
		assertEquals(3, foundOrder.getOrderItemList().size());
		
	}

	public Order createOrder() {
		Order order = new Order();
		
		for(int i = 0; i < 3; i++) {
			Item item = this.createItem();
			Item savedItem = itemRepository.save(item);
			
			OrderItem orderItem = OrderItem.builder()
					                           .order(order)
			                               .item(item)
			                               .orderPrice(500000)
			                               .count(5)
			                               .build();
			 
			order.getOrderItemList().add(orderItem);
		}
		log.info(">>>>>>>>>>>>>>>> order (Entity) : " + order);
		
		// Entity
		Member member = Member.builder().build();
		log.info(">>>>>>>>>>>>>>>> member (Entity) : " + member);
	
		Member savedMember = memberRepository.save(member);
		log.info(">>>>>>>>>>>>>>>> savedMember : " + savedMember);
		log.info(">>>>>>>>>>>>>>>> member (Entity) : " + member);
		
		order.setMember(member);
		Order savedOrder = orderRepository.save(order);
		log.info(">>>>>>>>>>>>>>>> savedOrder : " + savedOrder);
		log.info(">>>>>>>>>>>>>>>> order (Entity) : " + order);
		
		Order foundOrder = orderRepository.findById(savedOrder.getId())
		                                  .orElseThrow(EntityNotFoundException::new);
		log.info(">>>>>>>>>>>>>>>> foundOrder : " + foundOrder);
		
		return order;
	}
	
	
	@Test
	@DisplayName("Orphan Object remove test")
	public void orphanObjectRemoveTest() {
		
		// createOrder() 을 호출하면 주문서(Order) 에 주문상품(orderItem) 이 3 개가 설정(저장)됨
		Order order = this.createOrder();
		
		log.info(">>>>>>>>>>>>>>>> order item 개수 : " + order.getOrderItemList().size());
		order.getOrderItemList().remove(0);
		
		log.info(">>>>>>>>>>>>>>>> order item 개수 : " + order.getOrderItemList().size());
		entityManager.flush();
		
	}
	
	
	@Test
	@DisplayName("LAZY 와 EAGER 의 성능 테스트")
	public void lazyEagerTest() {
		Order order = this.createOrder();
		
		Long orderItemId = order.getOrderItemList().get(0).getId();
		
		entityManager.flush();
		entityManager.clear();
		
		OrderItem foundOrderItem = orderItemRepository.findById(orderItemId)
				                                          .orElseThrow(EntityNotFoundException::new);
		
		log.info(">>>>>>>>>>>>>>>> Order Class : " + foundOrderItem.getOrder().getClass());
		
	}
	

}




