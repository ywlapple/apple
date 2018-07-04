package com.fish.apple.user.control;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fish.apple.core.common.api.Response;
import com.fish.apple.user.vo.User;

@RestController
@RequestMapping("/user")
public class UserController {

	@RequestMapping(method = RequestMethod.GET , path="/")
	public Response<User> get(){
		return new Response<>();
	}
	
}
