package com.fish.apple.user.vo;


import java.util.List;

import com.fish.apple.user.bo.Account;
import com.fish.apple.user.bo.Menu;
import com.fish.apple.user.bo.Org;
import com.fish.apple.user.bo.Person;
import com.fish.apple.user.bo.Role;

import lombok.Data;

@Data
public class User {
	private Account account;
	private Person person; 
	private List<Org> orgs;
	private List<Menu> menus;
	private List<Role> roles;
	
	
	
}
