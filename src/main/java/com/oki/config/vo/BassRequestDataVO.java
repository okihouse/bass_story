package com.oki.config.vo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class BassRequestDataVO {

	private String tid;
	private String method;
	private String requestURI;
	private String queryString;
	private String contentType;
	private String userAgent;
	private int contentLength;
	private long timestamp;
	private long apiStartTime;
	private long apiEndTime;
	private Map<String,String[]> parameterMaps;

	private HttpServletRequest request;
	private HttpServletResponse response;
	public void put(String tid, String method, String requestURI, String queryString, String contentType, String userAgent,
			int contentLength, Map<String, String[]> parameterMaps) {
		this.tid = tid;
		this.method = method;
		this.requestURI = requestURI;
		this.queryString = queryString;
		this.contentType = contentType;
		this.userAgent = userAgent;
		this.contentLength = contentLength;
		this.parameterMaps = parameterMaps;
	}
	
}
