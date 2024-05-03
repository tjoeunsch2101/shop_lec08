package com.tjoeun.shop.dto;

import java.time.LocalDateTime;

import com.tjoeun.shop.constant.ItemSellStatus;
import com.tjoeun.shop.entity.ItemImage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto {

	private Long id;

  private String itemName; 
	
  private int price;
	
  private int stockNumber; 
	
  private String itemDetail; 
	
  private ItemSellStatus itemSellStatus;
  
  private LocalDateTime regTime;
  
  private LocalDateTime updateTime;
  
  
}
