package com.fish.apple.core.web.env;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import org.slf4j.MDC;

import com.fish.apple.core.common.constant.Constant;

import lombok.Data;

@Data
public class Environment {

	private static ThreadLocal<Environment> currentEnv = new ThreadLocal<>();
	
	private User user;
	private WebEnv webEnv;
	private Locale locale = Locale.getDefault();
	private String operId;
	private long startTime;
	private long endTime;
	private int level;
	
	
	
	public static Environment currentEnv() {
        return currentEnv.get() ;
	}
	public static WebEnv currentWebEnv() {
        return currentEnv.get().getWebEnv() ;
	}	
	public static String currentOperId() {
        return currentEnv.get().getOperId() ;
	}
	public static String currentTenantCode() {
		return currentEnv.get().getUser().getTenantCode() ;
	}
	public static User currentUser() {
		return currentEnv.get().getUser();
	}
	public static String currentUserNo() {
		return currentEnv.get().getUser().getUserNo();
	}
	public static Locale currentLocale() {
		return currentEnv.get().getLocale();
	}
	
	protected static Environment initEnv(String tenantCode , String userNo ,String url) {
		User user = new User();
		user.setTenantCode(tenantCode);
		user.setUserNo(userNo);
		WebEnv webEnv = new WebEnv();
		webEnv.setPath(url);
		return initEnv(webEnv , user, null);
	}
	
	protected static Environment initEnv(WebEnv webEnv , User user , String operId ) {
		
		if(null == user ) {
			user = User.systemUser;
		}else {
			String userNo = user.getUserNo();
			if(null == userNo || "".equals(userNo)) {
				user.setUserNo(User.systemUser.getUserNo());
			}
			String tenantCode = user.getTenantCode();
			if(null == tenantCode || "".equals(tenantCode)) {
				user.setTenantCode(User.systemUser.getTenantCode());
			}
		}
		if(null == operId || "".equals(operId)) {
			operId = UUID.randomUUID().toString().replaceAll("-", "");
		}
		if(null == webEnv) {
			webEnv = new WebEnv();
		}
		
		Environment env = new Environment();
		env.setUser(user);
		env.setOperId(operId);
		env.setStartTime(System.currentTimeMillis());
		env.setLevel(1);
		
		env.setWebEnv(webEnv);
		currentEnv.set(env);
		return env;
	}
	
	
	protected static void initChildEvn(Environment parentEnv) {
		Environment env = new Environment();
		env.setUser(parentEnv.getUser());
		String operaId = parentEnv.getOperId() ;
		operaId = operaId + "-" + UUID.randomUUID().toString().replaceAll("-", "");
		env.setOperId(operaId);
		WebEnv parengWebEnv = parentEnv.getWebEnv();
		env.setWebEnv(parengWebEnv);
		
		env.setStartTime(System.currentTimeMillis());
		env.setLevel(parentEnv.getLevel() + 1);
		currentEnv.set(env);
		initLogEnv();
	}
	
	public static Runnable initRunnableEnv(Runnable runnable) {
		return new EnvRunnable(runnable , currentEnv() );
	}
	public static void initLogEnv() {
		MDC.put(Constant.threadId.getCode(), Environment.currentEnv().getOperId());
		MDC.put(Constant.urlPath.getCode(), Environment.currentWebEnv().getPath());
		MDC.put(Constant.httpMethod.getCode(), Environment.currentWebEnv().getHttpMethod());
		MDC.put(Constant.startTime.getCode(), format.format(new Date(Environment.currentEnv().getStartTime())));
	}
	public static void initLogEnv(Method method) {
		WebEnv currentWebEnv = Environment.currentWebEnv();
		currentWebEnv.setController(method.getDeclaringClass().getName());
		currentWebEnv.setMethod(method.getName());
		initLogEnv();
	}
	private static final String pattern = "yy-MM-dd HH:mm:ss.SSS";
	private static final SimpleDateFormat format = new SimpleDateFormat(pattern);
	
	public static void end() {
		
		Environment currentEnv = Environment.currentEnv();
		currentEnv.setEndTime(System.currentTimeMillis());
		MDC.put(Constant.time.getCode(), String.valueOf( (currentEnv.getEndTime() - currentEnv.getStartTime()) ) );
		MDC.put(Constant.startTime.getCode(), format.format(new Date(currentEnv.getStartTime())) );
		MDC.put(Constant.endTime.getCode(), format.format(new Date(currentEnv.getEndTime())) );
		
	}
}
