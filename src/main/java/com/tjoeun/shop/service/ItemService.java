package com.tjoeun.shop.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tjoeun.shop.dto.ItemAndImageDto;
import com.tjoeun.shop.dto.ItemImageDto;
import com.tjoeun.shop.entity.Item;
import com.tjoeun.shop.entity.ItemImage;
import com.tjoeun.shop.repository.ItemImageRepository;
import com.tjoeun.shop.repository.ItemRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 상품 등록하기
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ItemService {
	
	private final ItemRepository itemRepository;
	private final ItemImageRepository itemImageRepository;
	private final ItemImageService itemImageService;
	
	// Item 저장하기
	public Long saveItem(ItemAndImageDto itemAndImageDto, List<MultipartFile> itemImageFileList) throws IOException {
		
		// 상품 등록하기 : DB 에도 저장함
		Item item = itemAndImageDto.createItem();
		itemRepository.save(item);
		
		// 이미지 등록하기 : DB 에도 저장함
		for(int i = 0; i < itemImageFileList.size(); i++) {
			ItemImage itemImage = new ItemImage();
			itemImage.setItem(item);
			
			if(i == 0) {
				itemImage.setRepresentativeImage("Y");
			}else {
				itemImage.setRepresentativeImage("N");
			}
			
			itemImageService.saveItemImage(itemImage, itemImageFileList.get(i));
			
		}
		return item.getId();
		
	} // saveItem
	
	
	// 상품 수정하기 : return type  <-- Long (itemId) : 어떤 상품이 수정되었는지 알려줌
	public Long updateItem(ItemAndImageDto itemAndImageDto, List<MultipartFile> itemImageFile) {
		
		/*
		Item item = itemRepository.findById(itemAndImageDto.getId()).orElseThrow(() -> 
		                                new EntityNotFoundException("아이템이 없습니다"));
		 */
		
		Item item = itemRepository.findById(itemAndImageDto.getId())
				                      .orElseThrow(EntityNotFoundException::new);
		
		// Dirty Checking : JPA 의 특성 (cf. auto commit)
		// Entity 의 상태를 감지해서 
		// transaction 안에서 변경된 부분이 있으면
		//  ㄴ Persistence Context 안에 있는 Entity 를 대상으로 그 상태를 감지함
		// transaction 이 시작할 때와 transaction 이 끝날 때의 Entity 의 상태를 비교해서 
		// 이 변경 내용을 자동으로 DataBase 에 반영하는 JPA 의 기능
		//                ㄴ update query 를 자동으로 database 에 전달함
		item.updateItem(itemAndImageDto);
		
		List<Long> itemImageIdList = itemAndImageDto.getItemImageIdList();
		log.info(">>>>>>>>>>>>>>>> itemImage id 개수 : " + itemImageIdList.size());
		
		for(int i = 0; i < itemImageIdList.size(); i++) {
			                                // index 번호와 파일을 전달해서 수정함
			itemImageService.updateItemImage(itemImageIdList.get(i), itemImageFile.get(i));
		}
		return item.getId();
	}
	
	
	public ItemAndImageDto getItemDetail(Long itemId) {
		
		// ItemImage Entity 를 element 로 하는 ArrayList
		List<ItemImage> itemImageList = 
				itemImageRepository.findByItemIdOrderById(itemId);
		
		log.info(">>>>>>>>>>>>>>>> itemImage 개수 : " + itemImageList.size());
		
		// ItemImage Entity 를 element 로 하는 ArrayList 를 ItemImageDto 로 ...
		List<ItemImageDto> itemImageDtoList =	new ArrayList<>();
		
		for(ItemImage itemImage : itemImageList) {
			ItemImageDto itemImageDto = ItemImageDto.entityToDto(itemImage);
			itemImageDtoList.add(itemImageDto);			
		}
		
		log.info(">>>>>>>>>>>>>>>> itemImageDto 개수 : " + itemImageDtoList.size());
		
		Item item = 
				itemRepository.findById(itemId).orElseThrow(
						() -> new EntityNotFoundException("해당 상품이 없습니다 (id : " + itemId + ")"));
		
		ItemAndImageDto itemAndImageDto = ItemAndImageDto.entityToDto(item);
		itemAndImageDto.setItemImageDtoList(itemImageDtoList);
		
		log.info(">>>>>>>>>>>>>>>> itemAndImageDto : " + itemAndImageDto);
		
		return itemAndImageDto;
	}
	

} // class








