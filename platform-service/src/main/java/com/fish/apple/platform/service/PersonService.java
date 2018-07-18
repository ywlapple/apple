package com.fish.apple.platform.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.transaction.annotation.Transactional;

import com.fish.apple.core.web.env.Environment;
import com.fish.apple.platform.bo.Account;
import com.fish.apple.platform.bo.Person;
import com.fish.apple.platform.repository.AccountRepository;
import com.fish.apple.platform.repository.PersonRepository;

public class PersonService {
	@Autowired
	private PersonRepository repository ;
	@Autowired
	private AccountRepository accountRepository ;
	
	@Transactional
	public Person save(Person person) {
		String personNo = person.getPersonNo();
		
		if(StringUtils.isNotBlank(personNo)) {
			Person qry = new Person();
			qry.setPersonNo(personNo);
			Example<Person> example = Example.of(qry);
			Optional<Person> personOp = repository.findOne(example);
			
			if(personOp.isPresent()) {
				person.setId(personOp.get().getId());
			};
			person = repository.saveAndFlush(person);
			//账号关联
			Account qrry = new Account();
			qrry.setAccountNo(Environment.currentAccountNo());
			Example<Account> accExample = Example.of(qrry);
			Optional<Account> accountOp = accountRepository.findOne(accExample);
			if(accountOp.isPresent()) {
				Account exit = accountOp.get();
				qrry.setId(exit.getId());
				qrry.setPersonNo(person.getPersonNo());
				qrry.setAuth(true);
				accountRepository.save(qrry);
			}
		} else {
			person = repository.saveAndFlush(person);
		}
		return person ;
	}
}
