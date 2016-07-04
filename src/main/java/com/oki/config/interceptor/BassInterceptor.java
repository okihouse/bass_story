package com.oki.config.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.oki.config.constant.BassConstant;
import com.oki.config.log.BassLogConstant;
import com.oki.config.log.BassLogManager;
import com.oki.config.vo.BassRequestDataVO;

@Component
public class BassInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(BassInterceptor.class);
	
	@Autowired
	private BassRequestDataVO bassRequestDataVO;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		String tid = request.getHeader(BassConstant.TID_HEADER_NAME);
		if (tid == null || tid.isEmpty()) {
			tid = BassConstant.TID_DEFAULT_VALUE;
		}
		
		String method = request.getMethod();
		String requestURI = request.getRequestURI();
		String queryString = request.getQueryString();
		String contentType = request.getContentType();
		String userAgent = request.getHeader(BassConstant.USER_AGENT_HEADER_NAME);
		int contentLength = request.getContentLength();
		Map<String, String[]> parameterMaps = request.getParameterMap();

		bassRequestDataVO.put(tid, method, requestURI, queryString, contentType, userAgent, contentLength, parameterMaps);
		bassRequestDataVO.setApiStartTime(System.currentTimeMillis());
		bassRequestDataVO.setRequest(request);
		bassRequestDataVO.setResponse(response);
		
		logger.info(BassLogManager.makeLog(4, BassLogConstant.MODULE_NAME, BassLogConstant.IP_ADDRESS, BassLogConstant.REQ, tid,
										BassLogConstant.HEADER_USER_AGENT, userAgent,
										BassLogConstant.HEADER_CONTENT_TYPE, contentType,
										BassLogConstant.HEADER_CONTENT_LENGTH, contentLength,
										BassLogConstant.METHOD_NAME , method,
										BassLogConstant.URI_NAME, requestURI, "?", queryString
									   ));
		
		return true;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)	throws Exception {
		if (response.getStatus() != HttpStatus.OK.value()) return;
		
		long rcode = 0;
		long apiStartTime = bassRequestDataVO.getApiStartTime();
		long apiEndTime = System.currentTimeMillis();
		long elapsed = (apiEndTime - apiStartTime);
		String time = elapsed > 5000 ? elapsed + "ms, TOOLONG" :  elapsed + "ms";
		
		logger.info(BassLogManager.makeLog(4, BassLogConstant.MODULE_NAME, BassLogConstant.IP_ADDRESS, BassLogConstant.RES, bassRequestDataVO.getTid(),
				BassLogConstant.HTTP_STATUS, response.getStatus(),
				BassLogConstant.RETURN_CODE , rcode,
				BassLogConstant.ELAPSED_TIME, time
				));
	}

//	private long getCurrentTimestamp(String tid) {
//		long timestamp = 0L;
//		try {
//			Pattern pattern = Pattern.compile(DnbbConstant.TIMESTAMP_PATTERN);
//			Matcher matcher = pattern.matcher(tid);
//			if (matcher.find() == true) {
//				timestamp = (long) Integer.parseInt(matcher.group());
//			}
//		} catch (Exception e) {
//			logger.warn(BassLogManager.makeLog(e.getMessage(), e));
//		}
//		return timestamp;
//	}
}
