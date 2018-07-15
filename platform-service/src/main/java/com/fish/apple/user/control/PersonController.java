package com.fish.apple.user.control;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fish.apple.core.common.api.Response;
import com.fish.apple.core.common.exception.BussinessException;
import com.fish.apple.core.common.exception.Result;
import com.fish.apple.platform.bo.Person;
import com.fish.apple.user.repository.PersonRepository;
import com.fish.apple.user.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {
	
	@Autowired
	private PersonRepository repository ; 
	
	@Autowired
	private PersonService service ;
	
	@RequestMapping(method = RequestMethod.GET ,path="/one/{id}" )
	public Response<Person> getOne(@PathVariable Long id){
		if(null == id ) {
			throw BussinessException.create().kind(Result.IllegalArgument).msg("id").msg("id 不能为空");
		}
		Optional<Person> accountOp = repository.findById(id);
		Response<Person> resp = new Response<>(); 
		if(accountOp.isPresent()) {
			return resp.success(accountOp.get());
		}
		return resp;
	}
	@RequestMapping(method = RequestMethod.GET ,path="/bid/{personNo}" )
	public Response<Person> getOne(@PathVariable String personNo){
		if(StringUtils.isBlank(personNo)) {
			throw BussinessException.create().kind(Result.IllegalArgument).msg("personNo").msg("用户编码 不能为空");
		}
		Person account = repository.findByPersonNo(personNo);
		Response<Person> resp = new Response<>(); 
		resp.success(account);
		return resp;
	}
	
	@RequestMapping(method = RequestMethod.GET ,path="/list" )
	public Response<Page<Person>> getList(@RequestParam String accountNo , int size , int page){
		Page<Person> findAll = repository.findAll(null, PageRequest.of(page, size));
		Response<Page<Person>> resp = new Response<>();
		return resp.success(findAll);
	}
	
	@RequestMapping(method = RequestMethod.POST )
	public Response<Person> add(@RequestBody Person person){
		Assert.notNull(person , "帐户信息不能为空");
		Assert.notNull(person.getName() , "用户名称不能为空");
		person = service.save(person);
		Response<Person> resp = new Response<>();
		return resp.success(person);
	}
	@RequestMapping(method = RequestMethod.PUT )
	public Response<Person> upd(@RequestBody Person person){
		Assert.notNull(person , "用户信息不能为空");
		Assert.notNull(person.getId() , "Id不能为空");
		person = service.save(person);
		Response<Person> resp = new Response<>();
		return resp.success(person);
	}
}
