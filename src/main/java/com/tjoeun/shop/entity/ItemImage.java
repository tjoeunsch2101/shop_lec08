package com.tjoeun.shop.entity;

import com.tjoeun.shop.audit.BasicEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
  imageName : (저장되는) 이미지 파일 이름
  originalImageName : (원본) 이미지 파일 이름
  imageUrl : 이미지 파일 경로
  representativeImage : 대표 이미지인지 아닌지... boolean
*/

@Entity
@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemImage extends BasicEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="item_image_id", nullable=false)
	private Long id;
	
	private String imageName;
	private String originalImageName;
	private String imageUrl;
	private String representativeImage;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="item_id")
	private Item item;
	
	public void updateItemImage(String imageName, String originalImageName, String imageUrl) {
		this.imageName = imageName;
		this.originalImageName = originalImageName;
		this.imageUrl = imageUrl;
	}
	
}

