package com.fish.apple.platform.control;

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
import com.fish.apple.core.common.dict.Able;
import com.fish.apple.core.common.exception.BussinessException;
import com.fish.apple.core.common.exception.Result;
import com.fish.apple.core.web.env.Environment;
import com.fish.apple.platform.bo.Org;
import com.fish.apple.platform.repository.OrgRepository;

@RestController
@RequestMapping("/org")
public class OrgController {
	@Autowired
	private OrgRepository repository ; 
	
	@RequestMapping(method = RequestMethod.GET ,path="/one/{id}" )
	public Response<Org> getOne(@PathVariable Long id){
		if(null == id ) {
			throw BussinessException.create().kind(Result.IllegalArgument).msg("id").msg("id 不能为空");
		}
		Optional<Org> accountOp = repository.findById(id);
		Response<Org> resp = new Response<>(); 
		if(accountOp.isPresent()) {
			return resp.success(accountOp.get());
		}
		return resp;
	}
	@RequestMapping(method = RequestMethod.GET ,path="/bid/{orgNo}" )
	public Response<Org> getOne(@PathVariable String orgNo){
		if(StringUtils.isBlank(orgNo)) {
			throw BussinessException.create().kind(Result.IllegalArgument).msg("orgNo").msg("机构编码 不能为空");
		}
		Org org = repository.findByTenantNoAndOrgNo( Environment.currentTenantNo() ,orgNo);
		Response<Org> resp = new Response<>(); 
		resp.success(org);
		return resp;
	}
	
	@RequestMapping(method = RequestMethod.GET ,path="/tree/{orgNo}" )
	public Response<Org> getTree(@PathVariable String orgNo){
		if(StringUtils.isBlank(orgNo)) {
			throw BussinessException.create().kind(Result.IllegalArgument).msg("orgNo").msg("机构编码 不能为空");
		}
		Org org = repository.findByTenantNoAndOrgNo( Environment.currentTenantNo() ,orgNo);
		Response<Org> resp = new Response<>(); 
		resp.success(org);
		return resp;
	}	
	
	
	@RequestMapping(method = RequestMethod.POST )
	public Response<Org> save(@RequestBody Org org){
		Assert.notNull(org , "租户信息不能为空");
		Assert.notNull(org.getOrgName() , "租户户名称不能为空");
		org = repository.saveAndFlush(org);
		Response<Org> resp = new Response<>();
		return resp.success(org);
	}
	
	@RequestMapping(method = RequestMethod.DELETE,  path="/bid/{orgNo}" )
	public Response<Org> delete(@PathVariable String orgNo ){
		Assert.hasText(orgNo , "orgNo不能为空");
		Org org = repository.findByTenantNoAndOrgNo( Environment.currentTenantNo() ,orgNo);
		if(null != org) {
			org.setAble(Able.disable);
			repository.saveAndFlush(org) ;
		}
		Response<Org> resp = new Response<>();
		return resp.success(org);
	}	
	
	@RequestMapping(method = RequestMethod.GET ,path="/list" )
	public Response<Page<Org>> getList(@RequestParam String orgName ,@RequestParam int size ,@RequestParam int page){
		Org org = new Org();
		org.setTenantNo(Environment.currentTenantNo());
		org.setOrgName(orgName);
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher(orgName, GenericPropertyMatchers.contains());
		Example<Org> exam = Example.of(org , matcher);
		Page<Org> findAll = repository.findAll(exam, PageRequest.of(page, size));
		Response<Page<Org>> resp = new Response<>();
		return resp.success(findAll);
	}

	@RequestMapping(method = RequestMethod.POST ,path="/list" )
	public Response<Page<Org>> getList(@RequestBody PageableReq<Org> req){
		Page<Org> findAll = repository.findAll(req.getExample(), req.getPageRequest());
		Response<Page<Org>> resp = new Response<>();
		return resp.success(findAll);
	}
}
