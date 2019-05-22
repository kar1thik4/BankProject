package com.hcl.bank.TestService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hcl.bank.model.Transaction;
import com.hcl.bank.model.User;
import com.hcl.bank.repository.UserRepositoryIntf;
import com.hcl.bank.service.UserServImpl;



@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
	
	   @Mock
	   private UserRepositoryIntf  userRepository;
		@InjectMocks
		UserServImpl userService;
		
		
		@Test
		public void getUserById() {
			User user=new User(1,"karthik","raja","karthik","1234","kr@gmail",0.0);
			Mockito.when( userRepository.findUserById(1)).thenReturn(user);
			User user1=userService.findUserById(1);
			assertThat(user1).isEqualTo(user);
			assertEquals(user.getEmail(),user1.getEmail());
			
		
		}

		@Test
		public void whenValidUser() {
			User user=new User(1,"karthik","raja","karthik","1234","kr@gmail",0.0);
			Mockito.when( userRepository.findUserByUnameAndPasswd("karthik","1234")).thenReturn(user);
			User user1=userService.findUserByUnameAndPasswd(user);
		assertThat(user1).isEqualTo(user);
		}
		@Test
		public void saveUser() {
			User user=new User(1,"karthik","raja","karthik","1234","kr@gmail",0.0);
			Mockito.when( userRepository.save(user)).thenReturn(user);
			User user1=userService.saveUser(user);
			assertThat(user1).isEqualTo(user);
		}
		
		@Test
		public void testForPasswd() {
			List<Transaction> transactions=new ArrayList<>();

			User user= new User();
			user.setId(1);
			user.setEmail("kk@gmail.com");
			user.setFname("karthik");
			user.setLname("raj");
			user.setUname("ironMan");
			user.setDate(new Date());
			user.setPasswd("karthik");
			user.setBalance(100.0);
			transactions.add(new Transaction(1,"salary","credit",1000.0,2000.0,user));
			user.setTransactions(transactions);
			Date date=new Date();
			assertTrue(user.getPasswd()=="karthik");
			assertTrue(user.getBalance()==100.0);
			assertTrue(user.getFname()=="karthik");
			assertTrue(user.getUname()=="ironMan");
			assertTrue(user.getId()==1);
			assertTrue(user.getLname()=="raj");
			assertThat(user.getDate()).isAfterOrEqualsTo(date);
			assertTrue(user.getEmail()=="kk@gmail.com");
			assertEquals(user.getTransactions().size(),transactions.size());
		}
}
