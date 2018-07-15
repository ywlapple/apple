package com.fish.apple.user.control;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.fish.apple.platform.bo.rel.PersonTenant;
import com.fish.apple.user.repository.PersonTenantRepository;

@RestController
@RequestMapping("/personTenant")
public class PersonTenantController {
	
	@Autowired
	private PersonTenantRepository repository ;
	
	@RequestMapping(method = RequestMethod.GET ,path="/one/{id}" )
	public Response<PersonTenant> getOne(@PathVariable Long id){
		if(null == id ) {
			throw BussinessException.create().kind(Result.IllegalArgument).msg("id").msg("id 不能为空");
		}
		Optional<PersonTenant> accountOp = repository.findById(id);
		Response<PersonTenant> resp = new Response<>(); 
		if(accountOp.isPresent()) {
			return resp.success(accountOp.get());
		}
		return resp;
	}
	
	@RequestMapping(method = RequestMethod.GET ,path="/list" )
	public Response<List<PersonTenant>> getList(@RequestParam String personNo){
		Assert.hasText(personNo , "用户编码(PersonNo)不能为空");
		List<PersonTenant> list = repository.findByPersonNo(personNo);
		Response<List<PersonTenant>> resp = new Response<>();
		return resp.success(list);
	}
	@RequestMapping(method = RequestMethod.POST ,path="/list" )
	public Response<Page<PersonTenant>> getList(@RequestBody PageableReq<PersonTenant> req){
		Page<PersonTenant> findAll = repository.findAll(req.getExample(), req.getPageRequest());
		Response<Page<PersonTenant>> resp = new Response<>();
		return resp.success(findAll);
	}
	
	@RequestMapping(method = RequestMethod.POST )
	public Response<PersonTenant> relTenant(@RequestBody PersonTenant personTenant){
		Assert.notNull(personTenant , "帐户信息不能为空");
		Assert.notNull(personTenant.getPersonNo() , "用户编码不能为空");
		Assert.notNull(personTenant.getTenantNo() , "租户编码不能为空");
		PersonTenant exist = repository.findByTenantNoAndPersonNo(personTenant.getTenantNo(), personTenant.getPersonNo());
		if(null != exist) {
			personTenant.setId(exist.getId());
		}
		personTenant = repository.saveAndFlush(personTenant);
		Response<PersonTenant> resp = new Response<>();
		return resp.success(personTenant);
	}
	@RequestMapping(method = RequestMethod.DELETE , path="/bid/{personNo}" )
	public Response<PersonTenant> releaseTenant( @PathParam("personNo") String personNo){
		Assert.notNull(personNo , "用户编号不能为空");
		repository.deleteByTenantNoAndPersonNo(Environment.currentTenantNo(), personNo);
		Response<PersonTenant> resp = new Response<>();
		return resp.success(null);
	}	
	
}
