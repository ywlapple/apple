package com.fish.apple.core.common.exception;

import com.fish.apple.core.common.domain.EnumBase;

public enum Result implements EnumBase {
	Success("0" , "成功"),
	SystemError("E00000001" , "系统错误,错误详情为:[{0}]"),
	SystemErrorOG("Fsong") ,
	IllegalArgument("E0000005" , "参数{0}校验未通过,{1}"),
	ConnectTimeout("E000006" , "{0}连接超时")
	;
	private String code ;
	private String message;
	private String field;
	Result(String message){
		this.code = super.toString();
		this.message = message;
	}
	Result(String code , String message){
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
		for(EnumBase enumm : Result.values()) {
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
