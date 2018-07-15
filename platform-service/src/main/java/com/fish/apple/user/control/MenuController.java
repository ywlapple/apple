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
import com.fish.apple.platform.bo.Menu;
import com.fish.apple.user.repository.MenuRepository;

@RestController
@RequestMapping("/menu")
public class MenuController {
	@Autowired
	private MenuRepository repository ; 
	
	@RequestMapping(method = RequestMethod.GET ,path="/one/{id}" )
	public Response<Menu> getOne(@PathVariable Long id){
		if(null == id ) {
			throw BussinessException.create().kind(Result.IllegalArgument).msg("id").msg("id 不能为空");
		}
		Optional<Menu> accountOp = repository.findById(id);
		Response<Menu> resp = new Response<>(); 
		if(accountOp.isPresent()) {
			return resp.success(accountOp.get());
		}
		return resp;
	}
	
	@RequestMapping(method = RequestMethod.GET ,path="/bid/{menuNo}" )
	public Response<Menu> getOne(@PathVariable String menuNo){
		if(StringUtils.isBlank(menuNo)) {
			throw BussinessException.create().kind(Result.IllegalArgument).msg("menuNo").msg("用户编码 不能为空");
		}
		Menu account = repository.findByTenantNoAndMenuNo(Environment.currentTenantNo() , menuNo);
		Response<Menu> resp = new Response<>(); 
		resp.success(account);
		return resp;
	}
	
	@RequestMapping(method = RequestMethod.POST )
	public Response<Menu> save(@RequestBody Menu menu){
		Assert.notNull(menu , "租户信息不能为空");
		Assert.notNull(menu.getMenuName() , "租户户名称不能为空");
		menu = repository.saveAndFlush(menu);
		Response<Menu> resp = new Response<>();
		return resp.success(menu);
	}
	
	@RequestMapping(method = RequestMethod.DELETE,  path="/bid/{menuNo}" )
	public Response<Menu> delete(@PathParam("menuNo") String menuNo){
		Assert.notNull(menuNo , "menuNo不能为空");
		Menu menu = repository.findByTenantNoAndMenuNo(Environment.currentTenantNo(), menuNo);
		if(null != menu) {
			menu.setAble(Able.disable);
			repository.saveAndFlush(menu);
		}
		Response<Menu> resp = new Response<>();
		return resp.success(menu);
	}	
	
	@RequestMapping(method = RequestMethod.GET ,path="/list" )
	public Response<Page<Menu>> getList(@RequestParam String menuName ,@RequestParam int size ,@RequestParam int page){
		Menu menu = new Menu();
		menu.setTenantNo(Environment.currentTenantNo());
		menu.setMenuName(menuName);
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher(menuName, GenericPropertyMatchers.contains());
		Example<Menu> exam = Example.of(menu , matcher);
		Page<Menu> findAll = repository.findAll(exam, PageRequest.of(page, size));
		Response<Page<Menu>> resp = new Response<>();
		return resp.success(findAll);
	}

	@RequestMapping(method = RequestMethod.POST ,path="/list" )
	public Response<Page<Menu>> getList(@RequestBody PageableReq<Menu> req){
		Page<Menu> findAll = repository.findAll(req.getExample(), req.getPageRequest());
		Response<Page<Menu>> resp = new Response<>();
		return resp.success(findAll);
	}
	
}
