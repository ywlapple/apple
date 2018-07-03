package com.fish.apple.core.web.env;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvRunnable implements Runnable {
	public EnvRunnable (Runnable runnable ,Environment env) {
		Environment.initChildEvn(env);
		origin = runnable;
		log = LoggerFactory.getLogger(runnable.getClass());
	}
	private Logger log ;
	private Environment parentEnv ;
	private Runnable origin;
	@Override
	public void run() {
		Environment.initChildEvn(parentEnv);
		try {
			origin.run();
		}catch(Exception e) {
			log.error("asyn thread error" , e);
		}finally {
			Environment.end();
			log.info("asyn thread end");
		}
	}

}
