package com.fish.apple.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fish.apple.user.repository.PersonRepository;
import com.fish.apple.user.vo.User;

@Service
public class UserService {

	@Autowired
	private PersonRepository personRepository;
	
	
	/**
	 * 全量保存用户
	 * 包括
	 * @param user
	 * @return
	 */
	public boolean saveUser ( User user ) {
		
		
		
		return false;
	}
	
	public User getUser(String userId) {
		
	
		return null ;
	}
}
