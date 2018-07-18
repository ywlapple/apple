package com.fish.apple.platform.control;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
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
import com.fish.apple.platform.bo.rel.PersonRole;
import com.fish.apple.platform.bo.rel.PersonRoleOrg;
import com.fish.apple.platform.repository.PersonRoleOrgRepository;

@RestController
@RequestMapping("/personRoleOrg")
public class PersonRoleOrgController {
	
	@Autowired
	private PersonRoleOrgRepository repository ;
	
	@RequestMapping(method = RequestMethod.GET ,path="/one/{id}" )
	public Response<PersonRoleOrg> getOne(@PathVariable Long id){
		if(null == id ) {
			throw BussinessException.create().kind(Result.IllegalArgument).msg("id").msg("id 不能为空");
		}
		Optional<PersonRoleOrg> accountOp = repository.findById(id);
		Response<PersonRoleOrg> resp = new Response<>(); 
		if(accountOp.isPresent()) {
			return resp.success(accountOp.get());
		}
		return resp;
	}
	@RequestMapping(method = RequestMethod.GET ,path="/bid/{personNo}/{roleNo}/{orgNo}" )
	public Response<PersonRoleOrg> getOne(@PathVariable String personNo , @PathVariable String roleNo , @PathVariable String orgNo ){
		if(StringUtils.isBlank(personNo) ) {
			throw BussinessException.create().kind(Result.IllegalArgument).msg("personNo").msg("角色编号 不能为空");
		}
		if(StringUtils.isBlank(roleNo) ) {
			throw BussinessException.create().kind(Result.IllegalArgument).msg("roleNo").msg("菜单编号 不能为空");
		}
		if(StringUtils.isBlank(orgNo) ) {
			throw BussinessException.create().kind(Result.IllegalArgument).msg("orgNo").msg("机构编号 不能为空");
		}
		PersonRoleOrg personRole = repository.findByTenantNoAndPersonNoAndRoleNoAndOrgNo(Environment.currentTenantNo() , personNo , roleNo , orgNo);
		Response<PersonRoleOrg> resp = new Response<>(); 
		return resp.success(personRole);
	}	
	@RequestMapping(method = RequestMethod.GET ,path="/list" )
	public Response<List<PersonRoleOrg>> getList(@RequestParam String personNo){
		if(StringUtils.isBlank(personNo) ) {
			throw BussinessException.create().kind(Result.IllegalArgument).msg("personNo").msg("角色编号 不能为空");
		}
		List<PersonRoleOrg> list = repository.findByTenantNoAndPersonNo(Environment.currentTenantNo() ,personNo);
		Response<List<PersonRoleOrg>> resp = new Response<>();
		return resp.success(list);
	}
	@RequestMapping(method = RequestMethod.POST ,path="/list" )
	public Response<Page<PersonRoleOrg>> getList(@RequestBody PageableReq<PersonRoleOrg> req){
		Page<PersonRoleOrg> findAll = repository.findAll(req.getExample(), req.getPageRequest());
		Response<Page<PersonRoleOrg>> resp = new Response<>();
		return resp.success(findAll);
	}
	
	@RequestMapping(method = RequestMethod.POST )
	public Response<PersonRoleOrg> relTenant(@RequestBody PersonRoleOrg personRole){
		Assert.notNull(personRole , "帐户信息不能为空");
		Assert.notNull(personRole.getPersonNo() , "用户编码不能为空");
		Assert.notNull(personRole.getRoleNo() , "租户编码不能为空");
		PersonRoleOrg exist = repository.findByTenantNoAndPersonNoAndRoleNoAndOrgNo(Environment.currentTenantNo() ,personRole.getPersonNo(), personRole.getRoleNo() ,personRole.getOrgNo());
		if(null != exist) {
			personRole.setId(exist.getId());
		}
		personRole = repository.saveAndFlush(personRole);
		Response<PersonRoleOrg> resp = new Response<>();
		return resp.success(personRole);
	}
	@RequestMapping(method = RequestMethod.DELETE , path="/bid/{personNo}/{roleNo}/{orgNo}" )
	public Response<PersonRole> releaseTenant( @PathVariable String personNo , @PathVariable String roleNo , @PathVariable String orgNo ){
		Assert.notNull(personNo , "菜单编号不能为空");
		Assert.notNull(roleNo , "菜单编号不能为空");
		Assert.notNull(orgNo , "机构编号不能为空");
		repository.deleteByTenantNoAndPersonNoAndRoleNoAndOrgNo(Environment.currentTenantNo(), personNo , roleNo , orgNo);
		Response<PersonRole> resp = new Response<>();
		return resp.success(null);
	}	
	
}
