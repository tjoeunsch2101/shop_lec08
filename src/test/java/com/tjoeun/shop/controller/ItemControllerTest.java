package com.tjoeun.shop.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("상품 등록 페이지 권리자 권한 테스트")
	@WithMockUser(username="admin", roles="ADMIN")
	public void itemRegisterAdminTest() throws Exception{
		
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/register"))
		       .andDo(print())
		       .andExpect(status().isOk());
	}

	@Test
	@DisplayName("상품 등록 페이지 일반회원 권한 테스트")
	@WithMockUser(username="user", roles="USER")
	public void itemRegisterUserTest() throws Exception{
		
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/register"))
        	.andDo(print())
        	.andExpect(status().isFound());
	     // .andExpect(status().isForbidden());
	}

}




