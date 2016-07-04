package com.oki.config.filter;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		BassReloadableRequestFilter bassReloadableRequestFilter = new BassReloadableRequestFilter();
		filterRegistrationBean.setFilter(bassReloadableRequestFilter);
		filterRegistrationBean.setOrder(1);
		return filterRegistrationBean;
	}
	
}
