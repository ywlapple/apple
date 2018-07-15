package com.fish.apple.platform.exception;

import com.fish.apple.core.common.domain.EnumBase;
import com.fish.apple.core.common.exception.ExceptionKind;

public enum BackendException implements ExceptionKind {
	accountOfPersonUnExistInTenant("EBACK00001" , "该租户({0})下用户({1})账号不存在"),
	;
	private String code ;
	private String message;
	private String field;
	BackendException(String message){
		this.code = super.toString();
		this.message = message;
	}
	BackendException(String code , String message){
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
		for(EnumBase enumm : BackendException.values()) {
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
