package com.hcl.bank.controller;



import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.bank.model.Report;
import com.hcl.bank.model.Transaction;
import com.hcl.bank.model.User;
import com.hcl.bank.service.TransactionServIntf;
import com.hcl.bank.service.UserServIntf;

@RestController
public class UserController {

	@Autowired
	UserServIntf userServ;
	
	@Autowired
	TransactionServIntf transServ;
	
	@PostMapping("/save")
	public User registration(@RequestBody User user) {

		/*User user1= userServ.saveUser(user);*/
		
		/*if(user1!=null) {
			
			return "Saved successfully";
		}
		else {
			return "Enter a different user name or email";
		}*/
		return userServ.saveUser(user);
	}
	
	@PostMapping("/login")
	public String login(@RequestBody User user) {
		User user1=userServ.findUserByUnameAndPasswd(user);
		if(user1!=null){
			return "Success";
		}
		else{
			return "Invalid Credentials";
		}
	}

	
	@PostMapping(value="/Transaction/{id}")
	public String transaction(@PathVariable("id") int userId,@RequestBody Transaction transactions) {
		
		User user=userServ.findUserById(userId);
		String result="";
		
		if(transactions.getPayType().equals("debit")) {
			if(user.getBalance()>transactions.getAmount()) {
				double amount=user.getBalance()-transactions.getAmount();
				user.setBalance(amount);
				result="Amount Debited Successfully";
			}
			else {
				result="Insufficient Balance....";
			}
		}
		else {
			double amount=user.getBalance()+transactions.getAmount();
			user.setBalance(amount);
			result="Amount Credited Successfully";
		}
		transactions.setUser(user);
		transactions.setBalance(user.getBalance());
		transServ.save(transactions);
		return result;
	}
	
	@GetMapping("/history/{id}")
	public List<Transaction> transHistory(@PathVariable("id") int userId){
		
		
		return transServ.findById(userId);

	}
	
	@GetMapping("/report/{id}")
	public Set<Report> history(@PathVariable("id") int userId,Report report){
		
		List<Object[]> list=(List<Object[]>) transServ.findReportById(userId);

		Set<Report> list1=new HashSet<Report>();
		for (Object[] object : list) {
		
		if(object[4].equals("May")) {
	for (int i = 0; i <= 4; i++) {
		if(i==0) {
			report.setUser_id((int) object[i]);
		}
		else if(i==1) {
			report.setCredits((Double) object[i]);
		}
		else if(i==2) {
			report.setDebits((Double) object[i]);
		}
		else if(i==3) {
			report.setBalance((Double) object[i]);
		}
		else {
			report.setMonth((String) object[i]);
		}
		
	}
	list1.add(report);
		}
		

		
		}
		
		
		return list1;

	}
	
}
