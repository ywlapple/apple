package com.fish.apple.core.common.constant;

import com.fish.apple.core.common.domain.EnumBase;

public enum Property implements EnumBase {
	tenantCode("tenantCode" , "租户编码"),
	multiMediaCode("multiMediaCode" , "多媒体编码"   ) ,
	fileType("fileType" , "文件类型"),
	name("name" , "名称"),
	attributeValueNo("attributeValueNo" , "属性值编码"), 
	attributeSetNo("attributeSetNo" , "属性集编码"),
	attributeNo("attributeNo" , "属性编码")	
	;
	private String code ;
	private String message;
	private String field;
	Property(){
		this.code = super.toString();
		this.message = this.code;
	}
	Property(String code , String message){
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
		for(EnumBase enumm : Property.values()) {
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
