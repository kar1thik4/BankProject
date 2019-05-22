package com.hcl.bank.service;

import com.hcl.bank.model.User;

public interface UserServIntf {

	User saveUser(User user);

	User findUserByUnameAndPasswd(User user);

	User findUserById(int userId);

}
