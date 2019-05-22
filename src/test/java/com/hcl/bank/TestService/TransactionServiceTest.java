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
import com.hcl.bank.repository.TransactionRepoIntf;
import com.hcl.bank.repository.UserRepositoryIntf;
import com.hcl.bank.service.TransactionServImpl;
import com.hcl.bank.service.UserServImpl;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceTest {

	@Mock
	private TransactionRepoIntf transRepo;
	@InjectMocks
	TransactionServImpl transServ;
	@Mock
	private UserRepositoryIntf  userRepository;
	@InjectMocks
	UserServImpl userService;

	@Test
	public void getAllTransactionsByUserId() {
		List<Transaction> transactions=new ArrayList<>();
		User user=new User(1,"karthik","raja","karthik","1234","kr@gmail",0.0);
		Mockito.when( userRepository.findUserById(1)).thenReturn(user);
		User user1=userService.findUserById(1);
		transactions.add(new Transaction(1,"salary","credit",1000.0,2000.0,user1));
		Mockito.when(transRepo.findTransactionById(1)).thenReturn(transactions);
		List<Transaction> transaction=transServ.findById(1);
		assertThat(transaction.size()).isEqualTo(1);
		assertEquals(transaction.size(),transactions.size());
	}
	
	@Test
	public void saveTransaction() {
		User user=new User(1,"karthik","raja","karthik","1234","kr@gmail",0.0);
		Mockito.when( userRepository.findUserById(1)).thenReturn(user);
		User user1=userService.findUserById(1);
		Transaction trans = new Transaction(1,"salary","credit",1000.0,2000.0,user1);
		Mockito.when(transRepo.save(trans)).thenReturn(trans);
		boolean trans1= transServ.save(trans);
		boolean str;
		if(trans.getTid()>=1) {
			str=true;
		}
		else
		{
			str=false;
		}
		assertTrue(trans1);
		assertTrue(str);
		
	}
	
	@Test 
	public void testForTransaction() {
		Transaction trans = new Transaction();
		trans.setTid(1);
		trans.setDescription("Salary");
		trans.setPayType("Credit");
		trans.setAmount(1000.0);
		trans.setBalance(2000.0);
		trans.setDate(new Date());
		User user=new User(1,"karthik","raja","karthik","1234","kr@gmail",0.0);
		
		trans.setUser(user);
		Date date=new Date();
		assertTrue(trans.getTid()==1);
		assertTrue(trans.getDescription()=="Salary");
		assertTrue(trans.getPayType()=="Credit");
		assertTrue(trans.getAmount()==1000.0);
		assertTrue(trans.getBalance()==2000.0);
		assertThat(trans.getDate()).isAfterOrEqualsTo(date);
		assertThat(trans.getUser()).isEqualTo(user);
		
	}
}