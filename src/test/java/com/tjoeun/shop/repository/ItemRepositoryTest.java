package com.tjoeun.shop.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tjoeun.shop.constant.ItemSellStatus;
import com.tjoeun.shop.entity.Item;
import com.tjoeun.shop.entity.QItem;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class ItemRepositoryTest {
	
	@Autowired
	private ItemRepository itemRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Test
	@DisplayName("Item 객체 생성 테스트")
	public void createItemTest() {
		
		Item item = Item.builder()
				            .itemName("더조은 상품")
				            .price(30000)
				            .stockNumber(300)
				            .itemDetail("더조은 상품 상세 설명")
				            .itemSellStatus(ItemSellStatus.SELL)
				         // .regTime(LocalDateTime.now())
				         // .updateTime(LocalDateTime.now())
				            .build();
		
		log.info(">>>>>>>>>>>>>>>> item : " + item);		
		Item savedItem = itemRepository.save(item);		
		log.info(">>>>>>>>>>>>>>>> savedItem : " + savedItem);
	}
	
	public void createItemList() {
		for(int i = 1; i <= 10; i++) {
			Item item = Item.builder()
          .itemName("더조은 상품 " + i)
          .price(30000 + i)
          .stockNumber(300)
          .itemDetail("더조은 상품 상세 설명 " + i)
          .itemSellStatus(ItemSellStatus.SELL)
       // .regTime(LocalDateTime.now())
       // .updateTime(LocalDateTime.now())
          .build();
		   Item savedItem = itemRepository.save(item);	   
		}
	}
	
	@Test
	@DisplayName("상품명으로 조회하는 테스트")
	void findByItemNameTest() {
		createItemList();		
		List<Item> itemList = itemRepository.findByItemName("더조은 상품 1");
		for(Item item : itemList) {
			log.info(">>>>>>>>>>>>>>>> item : " + item);
		}
		
		/* itemRepository.findByItemName("더조은 상품 1").forEach(System.out::println);
		itemRepository.findByItemName("더조은 상품 1")
		              .forEach(item -> System.out.println(item));
		itemRepository.findByItemName("더조은 상품 1")
		              .forEach(item -> log.info(">>>>>>>>>>>>>>>> item : " + item));
		*/
		
	}
	
	@Test
	@DisplayName("상품명 또는 상품상세설명으로 조회하는 테스트")
	public void findByItemNameOrItemDetailTest() {
		createItemList();		
		List<Item> itemList = 
				itemRepository.findByItemNameOrItemDetail("더조은 상품 8", "더조은 상품 상세 설명 5");
		/*
		for(Item item : itemList) {
			log.info(">>>>>>>>>>>>>>>> item : " + item);
		}
		*/
		itemList.forEach(item -> log.info(">>>>>>>>>>>>>>>> item : " + item));
		
		
	}
	
	@Test
	@DisplayName("LessThan 조건 검색")
	public void findByPriceLessThanTest() {
		createItemList();		
		List<Item> itemList = itemRepository.findByPriceLessThan(32000);
		itemList.forEach(item -> log.info(">>>>>>>>>>>>>>>> item : " + item));
	}
	
	
	// List<Item> findByPriceLessThanOrderByPriceDesc(Integer price)
	@Test
	@DisplayName("LessThan 조건 + OrderBy 검색")
	public void findByPriceLessThanOrderByPriceDescTest() {
		createItemList();
		List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(30005);
		itemList.forEach(item -> log.info(">>>>>>>>>>>>>>>> item : " + item));
	}

	@Test
	@DisplayName("상품상세설명으로 조회하기 - JPQL")
	public void findByDetailTest() {
		createItemList();
		List<Item> itemList = itemRepository.findByDetail("1");
		System.out.println("itemList" + itemList);
		itemList.forEach(item -> log.info(">>>>>>>>>>>>>>>> item : " + item));
		
	}
	
	@Test
	@DisplayName("상품상세설명으로 조회하기 - native Query")
	public void findByDetailNativeTest() {
		createItemList();
		List<Item> itemList = itemRepository.findByDetailNative("1");
		System.out.println("itemList" + itemList);
		itemList.forEach(item -> log.info(">>>>>>>>>>>>>>>> item : " + item));
		
	}
	
	@Test
	@DisplayName("querydsl Test")
	public void querydslTest() {
		createItemList();
		JPAQueryFactory query = new JPAQueryFactory(entityManager);
		// QItem qItemQItem.item;
		// JPAQuery<Tuple> queryResult= query.select().from().where().orderBy();
		// JPAQuery<Tuple> queryResult= query.selectFrom(QItem.item).where().orderBy();
		
		List<Item> itemList =  query.selectFrom(QItem.item)
				                        .where(QItem.item.itemSellStatus.eq(ItemSellStatus.SELL))
				                        .where(QItem.item.itemDetail.like("%" + 1 + "%"))
				                        .orderBy(QItem.item.price.desc())
				                        .fetch();
		itemList.forEach(item -> log.info(">>>>>>>>>>>>>>>> item : " + item));
	}
	
	public void createItemList2() {
		for(int i = 1; i <= 5; i++) {
			Item item = Item.builder()
          .itemName("더조은 상품 " + i)
          .price(30000 + i)
          .stockNumber(300)
          .itemDetail("더조은 상품 상세 설명 " + i)
          .itemSellStatus(ItemSellStatus.SELL)
       // .regTime(LocalDateTime.now())
       // .updateTime(LocalDateTime.now())
          .build();
		   Item savedItem = itemRepository.save(item);	   
		}
		for(int i = 6; i <= 10; i++) {
			Item item = Item.builder()
          .itemName("더조은 상품 " + i)
          .price(30000 + i)
          .stockNumber(300)
          .itemDetail("더조은 상품 상세 설명 " + i)
          .itemSellStatus(ItemSellStatus.SOLD_OUT)
       // .regTime(LocalDateTime.now())
       // .updateTime(LocalDateTime.now())
          .build();
		   Item savedItem = itemRepository.save(item);	   
		}
	}
	
	@Test
	@DisplayName("querydsl Test 2")
	public void  querydslTest2() {
		createItemList2();
		
		BooleanBuilder builder = new BooleanBuilder();
		String itemDetail = "상세";
		int price = 30003; 
		String itemSellStatus = "SELL";
		
		QItem qItem = QItem.item;
		
		builder.and(qItem.itemDetail.like("%"+ itemDetail +"%"));
		builder.and(qItem.price.gt(price));
		
		/*
		if(StringUtils.equals(itemSellStatus, ItemSellStatus.SELL)) {
			builder.and(qItem.itemSellStatus.eq(ItemSellStatus.SELL));
		}
		*/
		
		Pageable pageable = PageRequest.of(0, 5);
		Page<Item> page = itemRepository.findAll(builder, pageable);
		List<Item> contentList = page.getContent();
		
		contentList.forEach(element -> log.info(">>>>>>>>>>>>>>>> element : " + element));
		
	}
	
	
}





