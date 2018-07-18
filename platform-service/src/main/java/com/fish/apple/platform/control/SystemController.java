package com.fish.apple.platform.control;

import java.util.Optional;

import javax.websocket.server.PathParam;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fish.apple.core.common.api.PageableReq;
import com.fish.apple.core.common.api.Response;
import com.fish.apple.core.common.dict.Able;
import com.fish.apple.core.common.exception.BussinessException;
import com.fish.apple.core.common.exception.Result;
import com.fish.apple.core.web.env.Environment;
import com.fish.apple.platform.bo.System;
import com.fish.apple.platform.repository.SystemRepository;

@RestController
@RequestMapping("/system")
public class SystemController {
	@Autowired
	private SystemRepository repository ; 
	
	@RequestMapping(method = RequestMethod.GET ,path="/one/{id}" )
	public Response<System> getOne(@PathVariable Long id){
		if(null == id ) {
			throw BussinessException.create().kind(Result.IllegalArgument).msg("id").msg("id 不能为空");
		}
		Optional<System> accountOp = repository.findById(id);
		Response<System> resp = new Response<>(); 
		if(accountOp.isPresent()) {
			return resp.success(accountOp.get());
		}
		return resp;
	}
	@RequestMapping(method = RequestMethod.GET ,path="/bid/{systemNo}" )
	public Response<System> getOne(@PathVariable String systemNo){
		if(StringUtils.isBlank(systemNo)) {
			throw BussinessException.create().kind(Result.IllegalArgument).msg("systemNo").msg("用户编码 不能为空");
		}
		System account = repository.findByTenantNoAndSystemNo(Environment.currentTenantNo() , systemNo);
		Response<System> resp = new Response<>(); 
		resp.success(account);
		return resp;
	}
	@RequestMapping(method = RequestMethod.POST )
	public Response<System> save(@RequestBody System system){
		Assert.notNull(system , "租户信息不能为空");
		Assert.notNull(system.getSystemName() , "租户户名称不能为空");
		system = repository.saveAndFlush(system);
		Response<System> resp = new Response<>();
		return resp.success(system);
	}
	
	@RequestMapping(method = RequestMethod.DELETE,  path="/bid/{systemNo}" )
	public Response<System> delete(@PathParam("systemNo") String systemNo){
		Assert.notNull(systemNo , "systemNo不能为空");
		System system = repository.findByTenantNoAndSystemNo(Environment.currentTenantNo(), systemNo);
		if(null != system) {
			system.setAble(Able.disable);
			repository.saveAndFlush(system);
		}
		Response<System> resp = new Response<>();
		return resp.success(system);
	}	
	
	
	@RequestMapping(method = RequestMethod.GET ,path="/list" )
	public Response<Page<System>> getList(@RequestParam String systemName ,@RequestParam int size ,@RequestParam int page){
		System system = new System();
		system.setTenantNo(Environment.currentTenantNo());
		system.setSystemName(systemName);
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher(systemName, GenericPropertyMatchers.contains());
		Example<System> exam = Example.of(system , matcher);
		Page<System> findAll = repository.findAll(exam, PageRequest.of(page, size));
		Response<Page<System>> resp = new Response<>();
		return resp.success(findAll);
	}

	@RequestMapping(method = RequestMethod.POST ,path="/list" )
	public Response<Page<System>> getList(@RequestBody PageableReq<System> req){
		Page<System> findAll = repository.findAll(req.getExample(), req.getPageRequest());
		Response<Page<System>> resp = new Response<>();
		return resp.success(findAll);
	}
}
