package com.fish.apple.platform.exception;

import com.fish.apple.core.common.domain.EnumBase;
import com.fish.apple.core.common.exception.ExceptionKind;

public enum LoginException implements ExceptionKind {
	accountUnExist		("EUSER00001" , "帐户({0})不存在"),
	accountUnActive		("EUSER00002" , "帐户({0})未激活"),
	accountFreeze		("EUSER00003" , "帐户({0})已冻结"),
	accountFinish		("EUSER00004" , "帐户({0})已注销"),
	passwordError		("EUSER00005" , "帐户({0})密码错误"),
	tokenError   		("EUSER00006" , "token建立失败"),
	tokenValidateError	("EUSER00007" , "token验证错误") ,
	tokenValidateFailure("EUSER00008" , "token验证失败"),
	tokenExpired		("EUSER00009" , "token过期")
	;
	private String code ;
	private String message;
	private String field;
	LoginException(String message){
		this.code = super.toString();
		this.message = message;
	}
	LoginException(String code , String message){
		this.code = code;
		this.message = message;
	}
	@Override
	public String getCode() {
		return this.code;
	}
	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public boolean equals(String code) {
		return (null == this.code|"".equals(code)) ? false : this.code.equals(code);
	}
	public static boolean contain(String code) {
		for(EnumBase enumm : LoginException.values()) {
			if(enumm.equals(code)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String getField() {
		if(null == field || field.trim().length() == 0) {
			this.field = this.getClass().getSimpleName();
		}
		return this.field;
	}
	public String toString() {
		return this.code;
	}
}
