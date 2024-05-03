package com.tjoeun.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.tjoeun.shop.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long>, 
                                        QuerydslPredicateExecutor<Item> {
	
	// 상품명으로 조회하기
	List<Item> findByItemName(String itemName);
	
	// 상품명과 상품상세설명으로 조회하기
	List<Item> findByItemNameOrItemDetail(String itemName, String itemDetail);
	
	// LessThan 조건 검색
	List<Item> findByPriceLessThan(Integer price);
	
	// LessThan 조건 검색 + OrderBy
	List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);
	
	// 상품상세설명으로 조회하기 - JPQL
	// 가격 오름차순 조건 추가
	// @Query("select i from Item i where i.itemDetail like %:itemDetail%")
	@Query("select item from Item item where item.itemDetail like %:itemDetail% order by item.price desc")
	List<Item> findByDetail(@Param("itemDetail") String itemDetail);

	// nativeQuery
	@Query(value="select * from Item item where item.item_detail like %:itemDetail% order by item.price desc", nativeQuery=true)
	List<Item> findByDetailNative(@Param("itemDetail") String itemDetail);

	
	
	
	
}
