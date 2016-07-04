package com.oki.config.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class BassReloadableRequestFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Nothing to do
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		BassReloadableRequestBody bassReloadableRequestBody = new BassReloadableRequestBody((HttpServletRequest)request);
		chain.doFilter(bassReloadableRequestBody, response);
	}

	@Override
	public void destroy() {
		// Nothing to do
	}

}
