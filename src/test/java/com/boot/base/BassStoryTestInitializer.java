package com.boot.base;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;

public class BassStoryTestInitializer implements ApplicationContextInitializer<GenericApplicationContext>{

	@Override
	public void initialize(GenericApplicationContext applicationContext) {
		applicationContext.getEnvironment().getSystemProperties().put("jasypt.encryptor.password", "BASS_STORY");
	}
	
}
