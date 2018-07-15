package com.fish.apple.core.common.constant;

import com.fish.apple.core.common.domain.EnumBase;

public enum Constant implements EnumBase {
	VISITOR( "游客"),
	PREFIX("_" , "分隔符"),
	REDIS_PREFIX_ACCOUNT_IDNO("ACCOUNT_IDNO_", "redis中用户帐户信息key（证件身份）前缀"),
	REDIS_PREFIX_ACCOUNT_VISITOR("VISITOR_" , "redis中用户帐户信息key（微信游客身份）前缀") ,
	commonId("commonId" , "通用id"),
	commonIdPattern("yyyyMMdd0000" , "通用id格式"),
	commonIdPrefix("BI" , "通用id前缀"),
	commonIdSuffix("" , "通用id后缀"),
	commonSequence("common" , "通用sequence名称"),
	sqlWildcard("%" , "sql通配符") ,
	threadId("THREAD_ID" , "线程Id"),
	urlPath("URL_PATH" , "url路径"),
	httpMethod("HTTP_METHOD" ,  "http请求方法") ,
	time("TIME" ,  "耗时") ,
	startTime("START_TIME" ,  "开始时间") ,
	endTime("END_TIME" ,  "结束时间") ,
	scheduleInstancePropertyKey("org.quartz.scheduler.instanceName: DefaultQuartzScheduler" , "调度器实例名称配置key") ,
	coreLog("com.fish.apple.core.log" , "核心包日志log名称") ,
	DomainBasePackage("com.fish.apple" , "jpa扫描包含业务id的entity的基础包路径"),
	
	;
	
	private String message;
	private String code;
	
	private String field ;
	
	Constant( String message){
		this.code = super.toString();
		this.message = message;
	}
	Constant(String code , String message){
		this.code = code;
		this.message = message;
	}
	
	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public String getField() {
		if(null == field || field.trim().length() == 0) {
			this.field = this.getClass().getSimpleName();
		}
		return this.field;
	}
	@Override
	public boolean equals(String code) {
		return (null == this.code|"".equals(code)) ? false : this.code.equals(code);
	}
	public static boolean contain(String code) {
		for(EnumBase enumm : Constant.values()) {
			if(enumm.equals(code)) {
				return true;
			}
		}
		return false;
	}
	@Override
	public String toString() {
		return getCode();
	}
}
