package com.tjoeun.shop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tjoeun.shop.constant.ItemSellStatus;
import com.tjoeun.shop.dto.ItemAndImageDto;
import com.tjoeun.shop.entity.Item;
import com.tjoeun.shop.entity.ItemImage;
import com.tjoeun.shop.repository.ItemImageRepository;
import com.tjoeun.shop.repository.ItemRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;


@SpringBootTest
@Transactional
@Slf4j
class ItemServiceTest {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private ItemImageRepository itemImageRepository;

	public List<MultipartFile> createMultipartFiles(){
		
		List<MultipartFile> multipartFileList = new ArrayList<>();
		
		for(int i = 0; i < 5; i++) {
			String path = "C:/shop/itemImages";
			String imageName = "image_" + i + ".jpg";
			MockMultipartFile multipartFile = 
					new MockMultipartFile(path, imageName, "image/jpg", new byte[] {1, 2, 3, 4});
			multipartFileList.add(multipartFile);
		}
		
		return multipartFileList;
	}
	
	@Test
	@DisplayName("상품 등록 테스트")
	@WithMockUser(username="admin", roles="ADMIN")
	public void saveItemTest() throws IOException {
		ItemAndImageDto itemAndImageDto = 
				ItemAndImageDto.builder()
				               .itemName("테스트 아이템")
				               .price(20000)
				               .stockNumber(300)
				               .itemDetail("테스트 아이템 상세 설명")
				               .itemSellStatus(ItemSellStatus.SELL)
				               .build();
		
		List<MultipartFile> multipartFileList = createMultipartFiles();
		
		Long itemId = itemService.saveItem(itemAndImageDto, multipartFileList);
		
		List<ItemImage> itemImageList = 
				itemImageRepository.findByItemIdOrderById(itemId);
		
		Item item = itemRepository.findById(itemId)
				                      .orElseThrow(EntityNotFoundException::new);
		
		log.info(">>>>>>>>>>>>>>>> item : " + item);
		
		assertEquals(itemAndImageDto.getItemName(), item.getItemName());
		assertEquals(itemAndImageDto.getPrice(), item.getPrice());
		assertEquals(itemAndImageDto.getStockNumber(), item.getStockNumber());
		assertEquals(itemAndImageDto.getItemDetail(), item.getItemDetail());
		assertEquals(itemAndImageDto.getItemSellStatus(), item.getItemSellStatus());
		
		
	}

}



