package com.tjoeun.shop.dto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.tjoeun.shop.constant.ItemSellStatus;
import com.tjoeun.shop.entity.Item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 
 id : 상품 코드
 itemName : 상품이름
 price : 상품가격
 stockNumber : 재고수량
 itemDetail : 상품설명
 itemSellStatus : 판매상태
 
 @Null :   null 만 허용
 @NotNull :   null 은 안 됨 / "", " " 은 허용
 @NotEmpty :  null, "", [] 안 됨, " " 은 허용
 @NotBlank :  null, "", " " 안 됨
 
 */
@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemAndImageDto {
	
	private Long id;

	@NotBlank(message="상품 이름은 꼭 입력해 주세요")
  private String itemName; 
	
	@NotNull(message="상품 가격은 꼭 입력해 주세요")
  private int price;
	
	@NotNull(message="재고 수량은 꼭 입력해 주세요")
  private int stockNumber; 
	
	@NotBlank(message="상품 상세 설명은 꼭 입력해 주세요")
  private String itemDetail; 
	
  private ItemSellStatus itemSellStatus;
  
  private List<ItemImageDto> itemImageDtoList = new ArrayList<>();
  
  private List<Long> itemImageIdList = new ArrayList<>();
  
  private static ModelMapper modelMapper = new ModelMapper();
  
  
  // DTO --> Entity
  public Item createItem() {
  	
  	Item item = modelMapper.map(this, Item.class);
  	return item;
  }
   
  // Entity --> Dto
  public static ItemAndImageDto entityToDto(Item item) {
  	
  	ItemAndImageDto itemAndImageDto = modelMapper.map(item, ItemAndImageDto.class);
  	return itemAndImageDto;
  }
  
   
   
   
   
}
