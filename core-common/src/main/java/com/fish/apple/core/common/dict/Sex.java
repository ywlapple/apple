package com.fish.apple.core.common.dict;

import com.fish.apple.core.common.EnumBase;

public enum Sex implements EnumBase {
	male( "男"),
	female("女")
	;
	private String field;//字典类型
	private String code ;//字典枚举值
	private String message;//枚举值信息
	Sex(String code , String message){
		this.code = code;
		this.message = message;
	}
	Sex(String message){
		this.code = super.toString();
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
		getCode();
		return (null == this.code|"".equals(code)) ? false : this.code.equals(code);
	}
	public static boolean contain(String code) {
		for(EnumBase enumm : Sex.values()) {
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
