package com.tjoeun.shop.service;

import java.io.IOException;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tjoeun.shop.entity.ItemImage;
import com.tjoeun.shop.repository.ItemImageRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

// 상품 이미지를 upload 하거나
// 상품 이미지 정보를 저장함  <-- itemImageLocation 사용함
@Service
@RequiredArgsConstructor
@Transactional
public class ItemImageService {
	
	private final ItemImageRepository itemImageRepository;
	private final FileService fileService;
	
	@Value("${itemImageLocation}")
	private String itemImageLocation;
	
	public void saveItemImage(ItemImage itemImage, MultipartFile itemImageFile) throws IOException {
		
		String originalImageName = itemImageFile.getOriginalFilename();
		String imageName = "";
		String imageUrl = "";
		
		// originalImageName 이 있는 경우 : StringUtils 의 isEmpty() 메소드 호출
		if(!StringUtils.isEmpty(originalImageName)) {
			imageName = fileService.uploadFile(itemImageLocation, originalImageName, itemImageFile.getBytes());
			imageUrl = "/images/item/" + imageName;
			
		}
		// ItemImage Entity Setting
		itemImage.updateItemImage(imageName, originalImageName, imageUrl);
		
	  // ItemImage Entity  --> DB
		itemImageRepository.save(itemImage);
		
		
	} // saveItemImage
	
	// 이미지 update
	public void updateItemImage(Long itemImageId, MultipartFile itemImageFile) {
		if(!itemImageFile.isEmpty()) {
			
			ItemImage itemImage = 
					itemImageRepository.findById(itemImageId).orElseThrow(EntityNotFoundException::new);
			
			// 파일 이름이 있는 경우
			if(!StringUtils.isEmpty(itemImage.getImageName())) {
				fileService.deleteFile(itemImageLocation + "/" + itemImage.getImageName());
			}
			
		}
		
	}  // updateItemImage

} // class



