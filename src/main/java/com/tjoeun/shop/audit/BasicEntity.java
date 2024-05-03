package com.tjoeun.shop.audit;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

//지정한 Entity(BasicTimeEntity) 는 Auditing 기능이 
//설정되었다는 것을 JPA에 알려줌
@EntityListeners(value= {AuditingEntityListener.class})
//자식 클래스에게 mapping 정보를 제공함
@MappedSuperclass
@Setter @Getter
public class BasicEntity extends BasicTimeEntity{
	
  @CreatedBy
  @Column(updatable=false)
	private String createdBy;
	
	@LastModifiedBy
	private String modifiedBy;
	
	
}
