package com.tjoeun.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tjoeun.shop.entity.ItemImage;

// ItemImage Entity 로 DB 작업하기
public interface ItemImageRepository extends JpaRepository<ItemImage, Long>{
	
	List<ItemImage> findByItemIdOrderById(Long itemId);
	

}
