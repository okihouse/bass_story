package com.oki.config.log;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BassLogConstant {
	
	private static final Logger logger = LoggerFactory.getLogger(BassLogConstant.class);
	
	public static String BASE_PACKAGE = "com";
	public static final String BLOCK_DELEMETER = " | ";
	public static final String VALUE_DELEMETER = "=";
	public static final String VALUE_DELEMETER_EXCEPTION = "\n \t at ";
	public static final String VALUE_DELEMETER_COMMA = ",";
	public static final String STRING_DELEMETER = " : ";
	
	public static String ip = "UNKNOWN-ADDRESS";
	static{
		try {
			ip = Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			logger.error(BassLogManager.makeLog("Could not obtain IP Address", e));
		}
		
		String fullPackageName = BassLogConstant.class.getCanonicalName();
		String rootPackageName = "";
		try {
			rootPackageName = fullPackageName.substring(0, fullPackageName.indexOf("."));
		} catch (Exception e) {
			logger.error(BassLogManager.makeLog("invalid package naming. package=" + fullPackageName, e));
			rootPackageName = "com";
		}
		BASE_PACKAGE = rootPackageName;
	}
	
	public static final String MODULE_NAME = "BASS";
	public static final String IP_ADDRESS = ip;
	public static final String REQ = "REQ";
	public static final String RES = "RES";
	public static final String HEADER_CONTENT_TYPE = "Content-Type";
	public static final String METHOD_NAME = "method";
	public static final String URI_NAME = "uri";
	public static final String QUESTION_MARK = "?";

	public static final String HTTP_STATUS = "status";
	public static final String RETURN_CODE = "rcode";
	public static final String RETURN_ERROR_CODE = " error code";
	public static final String RETURN_MSG = "rmsg";
	public static final String RETURN_ERROR_MSG = " error message";
	public static final String ELAPSED_TIME = "elapsed";

	public static final String NEXT_LINE = "\n";

	public static final Object HEADER_CONTENT_LENGTH = "Content-Length";

	public static final Object HEADER_USER_AGENT = "User-Agent";
}
