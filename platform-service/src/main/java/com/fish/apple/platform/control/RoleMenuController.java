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
import com.fish.apple.platform.bo.rel.RoleMenu;
import com.fish.apple.platform.repository.RoleMenuRepository;

@RestController
@RequestMapping("/roleMenu")
public class RoleMenuController {
	
	@Autowired
	private RoleMenuRepository repository ;
	
	@RequestMapping(method = RequestMethod.GET ,path="/one/{id}" )
	public Response<RoleMenu> getOne(@PathVariable Long id){
		if(null == id ) {
			throw BussinessException.create().kind(Result.IllegalArgument).msg("id").msg("id 不能为空");
		}
		Optional<RoleMenu> accountOp = repository.findById(id);
		Response<RoleMenu> resp = new Response<>(); 
		if(accountOp.isPresent()) {
			return resp.success(accountOp.get());
		}
		return resp;
	}
	@RequestMapping(method = RequestMethod.GET ,path="/bid/{roleNo}/{menuNo}" )
	public Response<RoleMenu> getOne(@PathVariable String roleNo , @PathVariable String menuNo){
		if(StringUtils.isBlank(roleNo) ) {
			throw BussinessException.create().kind(Result.IllegalArgument).msg("roleNo").msg("角色编号 不能为空");
		}
		if(StringUtils.isBlank(menuNo) ) {
			throw BussinessException.create().kind(Result.IllegalArgument).msg("menuNo").msg("菜单编号 不能为空");
		}
		RoleMenu roleMenu = repository.findByTenantNoAndRoleNoAndMenuNo(Environment.currentTenantNo() , roleNo , menuNo);
		Response<RoleMenu> resp = new Response<>(); 
		return resp.success(roleMenu);
	}	
	@RequestMapping(method = RequestMethod.GET ,path="/list" )
	public Response<List<RoleMenu>> getList(@RequestParam String roleNo){
		if(StringUtils.isBlank(roleNo) ) {
			throw BussinessException.create().kind(Result.IllegalArgument).msg("roleNo").msg("角色编号 不能为空");
		}
		List<RoleMenu> list = repository.findByTenantNoAndRoleNo(Environment.currentTenantNo() ,roleNo);
		Response<List<RoleMenu>> resp = new Response<>();
		return resp.success(list);
	}
	@RequestMapping(method = RequestMethod.POST ,path="/list" )
	public Response<Page<RoleMenu>> getList(@RequestBody PageableReq<RoleMenu> req){
		Page<RoleMenu> findAll = repository.findAll(req.getExample(), req.getPageRequest());
		Response<Page<RoleMenu>> resp = new Response<>();
		return resp.success(findAll);
	}
	
	@RequestMapping(method = RequestMethod.POST )
	public Response<RoleMenu> relTenant(@RequestBody RoleMenu roleMenu){
		Assert.notNull(roleMenu , "帐户信息不能为空");
		Assert.notNull(roleMenu.getRoleNo() , "用户编码不能为空");
		Assert.notNull(roleMenu.getMenuNo() , "租户编码不能为空");
		RoleMenu exist = repository.findByTenantNoAndRoleNoAndMenuNo(Environment.currentTenantNo() ,roleMenu.getRoleNo(), roleMenu.getMenuNo());
		if(null != exist) {
			roleMenu.setId(exist.getId());
		}
		roleMenu = repository.saveAndFlush(roleMenu);
		Response<RoleMenu> resp = new Response<>();
		return resp.success(roleMenu);
	}
	@RequestMapping(method = RequestMethod.DELETE , path="/bid/{roleNo}/{menuNo}" )
	public Response<RoleMenu> releaseTenant( @PathVariable String roleNo , @PathVariable String menuNo ){
		Assert.notNull(roleNo , "菜单编号不能为空");
		Assert.notNull(menuNo , "菜单编号不能为空");
		repository.deleteByTenantNoAndRoleNoAndMenuNo(Environment.currentTenantNo(), roleNo , menuNo);
		Response<RoleMenu> resp = new Response<>();
		return resp.success(null);
	}	
	
}
