package com.hcl.bank.testcontroller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.bank.model.User;
import com.hcl.bank.service.UserServIntf;



@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

	
	private MockMvc mockMvc;
	
	@MockBean
	private UserServIntf userServ;
	   @Autowired
	   WebApplicationContext webApplicationContext;

	   protected void setUp() {
		   mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	   }
	
	
	

	/*
	@Test
	public void getAllTransactions() throws Exception
	{
		
	  mvc.perform(
			 get("/history/{id}",1)
			 .accept(MediaType.APPLICATION_JSON))
			  .andExpect(status().isOk())
		      .andExpect(MockMvcResultMatchers.jsonPath("$.transaction").exists());
	}	 */
	
	
	@Test
	public void createUser() throws Exception {
		User user= new User();
		user.setId(1);
		user.setEmail("kk@gmail.com");
		user.setFname("karthik");
		user.setLname("raj");
		user.setUname("ironMan");
		user.setDate(new Date());
		user.setPasswd("karthik");
		user.setBalance(100.0);
		
		
		String inputJson = this.mapToJson(user);
		String uri="/INGBank/save";
		Mockito.when(userServ.saveUser(Mockito.any(User.class))).thenReturn(user);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.accept(MediaType.APPLICATION_JSON).content(inputJson)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		String outPutJson = response.getContentAsString();
		
		assertThat(outPutJson).isEqualTo(inputJson);
		assertEquals(HttpStatus.OK.value(),response.getStatus());
	
		
				
	}
	
	private String mapToJson (Object object) throws JsonProcessingException{
		ObjectMapper objectMapper= new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
	
}
