package com.fish.apple.user.control;

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
import com.fish.apple.platform.bo.Role;
import com.fish.apple.user.repository.RoleRepository;

@RestController
@RequestMapping("/role")
public class RoleController {
	@Autowired
	private RoleRepository repository ; 
	
	@RequestMapping(method = RequestMethod.GET ,path="/one/{id}" )
	public Response<Role> getOne(@PathVariable Long id){
		if(null == id ) {
			throw BussinessException.create().kind(Result.IllegalArgument).msg("id").msg("id 不能为空");
		}
		Optional<Role> accountOp = repository.findById(id);
		Response<Role> resp = new Response<>(); 
		if(accountOp.isPresent()) {
			return resp.success(accountOp.get());
		}
		return resp;
	}
	@RequestMapping(method = RequestMethod.GET ,path="/bid/{roleNo}" )
	public Response<Role> getOne(@PathVariable String roleNo){
		if(StringUtils.isBlank(roleNo)) {
			throw BussinessException.create().kind(Result.IllegalArgument).msg("roleNo").msg("用户编码 不能为空");
		}
		Role account = repository.findByTenantNoAndRoleNo(Environment.currentTenantNo() , roleNo);
		Response<Role> resp = new Response<>(); 
		resp.success(account);
		return resp;
	}
	@RequestMapping(method = RequestMethod.POST )
	public Response<Role> save(@RequestBody Role role){
		Assert.notNull(role , "租户信息不能为空");
		Assert.notNull(role.getRoleName() , "租户户名称不能为空");
		role = repository.saveAndFlush(role);
		Response<Role> resp = new Response<>();
		return resp.success(role);
	}
	
	@RequestMapping(method = RequestMethod.DELETE,  path="/bid/{roleNo}" )
	public Response<Role> delete(@PathParam("roleNo") String roleNo){
		Assert.notNull(roleNo , "roleNo不能为空");
		Role role = repository.findByTenantNoAndRoleNo(Environment.currentTenantNo(), roleNo);
		if(null != role) {
			role.setAble(Able.disable);
			repository.saveAndFlush(role);
		}
		Response<Role> resp = new Response<>();
		return resp.success(role);
	}	
	
	
	@RequestMapping(method = RequestMethod.GET ,path="/list" )
	public Response<Page<Role>> getList(@RequestParam String roleName ,@RequestParam int size ,@RequestParam int page){
		Role role = new Role();
		role.setTenantNo(Environment.currentTenantNo());
		role.setRoleName(roleName);
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher(roleName, GenericPropertyMatchers.contains());
		Example<Role> exam = Example.of(role , matcher);
		Page<Role> findAll = repository.findAll(exam, PageRequest.of(page, size));
		Response<Page<Role>> resp = new Response<>();
		return resp.success(findAll);
	}

	@RequestMapping(method = RequestMethod.POST ,path="/list" )
	public Response<Page<Role>> getList(@RequestBody PageableReq<Role> req){
		Page<Role> findAll = repository.findAll(req.getExample(), req.getPageRequest());
		Response<Page<Role>> resp = new Response<>();
		return resp.success(findAll);
	}
}
