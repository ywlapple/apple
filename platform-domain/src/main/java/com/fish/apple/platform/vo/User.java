package com.fish.apple.platform.vo;


import java.util.List;

import com.fish.apple.platform.bo.Account;
import com.fish.apple.platform.bo.Person;

import lombok.Data;

@Data
public class User {
	private String tenantNo;
	private Account account;
	private Person person;
	private List<com.fish.apple.platform.bo.System> systems;
	private String token ;
}
