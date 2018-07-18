package com.fish.apple.platform.control;

import java.util.Date;
import java.util.Optional;

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
import com.fish.apple.core.common.exception.BussinessException;
import com.fish.apple.core.common.exception.Result;
import com.fish.apple.core.web.env.Environment;
import com.fish.apple.platform.bo.Tenant;
import com.fish.apple.platform.repository.TenantRepository;

@RestController
@RequestMapping("/tenant")
public class TenantController {
	
	@Autowired
	private TenantRepository repository ; 
	
	@RequestMapping(method = RequestMethod.GET ,path="/one/{id}" )
	public Response<Tenant> getOne(@PathVariable Long id){
		if(null == id ) {
			throw BussinessException.create().kind(Result.IllegalArgument).msg("id").msg("id 不能为空");
		}
		Optional<Tenant> accountOp = repository.findById(id);
		Response<Tenant> resp = new Response<>(); 
		if(accountOp.isPresent()) {
			return resp.success(accountOp.get());
		}
		return resp;
	}
	@RequestMapping(method = RequestMethod.GET ,path="/bid/{tenantNo}" )
	public Response<Tenant> getOne(@PathVariable String tenantNo){
		if(StringUtils.isBlank(tenantNo) ) {
			throw BussinessException.create().kind(Result.IllegalArgument).msg("id").msg("id 不能为空");
		}
		Tenant account = repository.findByTenantNo(tenantNo);
		Response<Tenant> resp = new Response<>(); 
		return resp.success(account);
	}	
	@RequestMapping(method = RequestMethod.POST )
	public Response<Tenant> save(@RequestBody Tenant tenant){
		Assert.notNull(tenant , "租户信息不能为空");
		Assert.notNull(tenant.getTenantName() , "租户户名称不能为空");
		Date now = new Date();
		if(null == tenant.getId()) {
			tenant.setCreateTime(now);
			tenant.setCreateUser(Environment.currentPersonNo());
		}
		tenant.setUpdateTime(now);
		tenant.setUpdateUser(Environment.currentPersonNo());
		tenant = repository.saveAndFlush(tenant);
		Response<Tenant> resp = new Response<>();
		return resp.success(tenant);
	}
	
	@RequestMapping(method = RequestMethod.GET ,path="/list" )
	public Response<Page<Tenant>> getList(@RequestParam String tenantName ,@RequestParam int size ,@RequestParam int page){
		Tenant tenant = new Tenant();
		tenant.setTenantName(tenantName);
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher(tenantName, GenericPropertyMatchers.contains());
		Example<Tenant> exam = Example.of(tenant , matcher);
		Page<Tenant> findAll = repository.findAll(exam, PageRequest.of(page, size));
		Response<Page<Tenant>> resp = new Response<>();
		return resp.success(findAll);
	}

	@RequestMapping(method = RequestMethod.POST ,path="/list" )
	public Response<Page<Tenant>> getList(@RequestBody PageableReq<Tenant> req){
		Page<Tenant> findAll = repository.findAll(req.getExample(), req.getPageRequest());
		Response<Page<Tenant>> resp = new Response<>();
		return resp.success(findAll);
	}
}
