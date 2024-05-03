package com.tjoeun.shop.dtotest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Developer {
  private String name;
  private int age;
  private String subject;
  private String address;
}
