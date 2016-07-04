package com.oki.config.log;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BassLogManager {

	private static final Logger logger = LoggerFactory.getLogger(BassLogManager.class);
	
	/**
	 * Exception 이 발생할 경우 Exception의 내용 및 해당 Exception을 발생시킨 중요 Message를 표시하는 메소드
	 * @param msg 표시할 Message (e.g parameter, custom message, etc...)
	 * @param e Exception
	 * @return String message 와 exception 이 결합된 String (not null) 
	 */
	public static String makeLog(String msg, Exception e) {
		StringBuffer sb = new StringBuffer();
		sb.append(msg)
		.append(BassLogConstant.BLOCK_DELEMETER);
		convertException(sb, e);
		return sb.toString();
	}

	/**
	 * 특정 Object 의 모든것을 표시하는 메소드
	 * String[] 의 경우 모든 배열의 종류를 표시하며, List 인 경우 List 를 표시, Map 인 경우 Map 전체를 표시
	 * VO Class 인 경우 field 전체를 표시(단, field 의 경우 Public 접근제어자를 갖고있어야 함)
	 * @param msg 표시할 Message (e.g parameter, custom message, etc...) 
	 * @param value 표시할 특정 Object (e.g array, list, map, class)
	 * @return String message 와 Object의 모든것이 결합된 String (not null) 
	 */
	public static String makeLog(String msg, Object value) {
		StringBuffer sb = new StringBuffer();
		sb.append(msg)
		.append(BassLogConstant.BLOCK_DELEMETER);
		convertObject(sb, value);
		return sb.toString();
	}
	
	/**
	 * Exception이 발생한 경우 특정 Object 의 모든것을 표시하며 Exception 의 내용도 표시하는 메소드
	 * String[] 의 경우 모든 배열의 종류를 표시하며, List 인 경우 List 를 표시, Map 인 경우 Map 전체를 표시
	 * VO Class 인 경우 field 전체를 표시(단, field 의 경우 Public 접근제어자를 갖고있어야 함)
	 * @param value 표시할 특정 Object (e.g array, list, map, class)
	 * @param e Exception
	 * @return String Object의 모든것과 exception 이 결합된 String (not null) 
	 */
	public static String makeLog(Object value, Exception e) {
		StringBuffer sb = new StringBuffer();
		convertObject(sb, value);
		sb.append(BassLogConstant.BLOCK_DELEMETER);
		convertException(sb, e);
		return sb.toString();
	}
	
	/**
	 * AOP Logging 및 특정 구분자로 로그를 나타낼 때 사용되는 method
	 * @param count 구분자로 분리할 글자 개수(e.g count 값이 3인 경우 3개의 문자열을 | 로 나눔 - AAA | BBB | CCC)
	 * @param value 구분자로 분리할 parameter 및 그 외 parameters
	 * @return String 구분자로 분리된 문자 및 그 외 parameter가 정렬된 String (not null)  
	 */
	public static String makeLog(int count, Object...value) {
		StringBuffer sb = new StringBuffer();
		if (value == null || (count > value.length)) {
			sb.append("[Error] Delimiter size can not be greater than values size");
		} else {
			for (int i = 0; i < count; i++) {
				sb.append(value[i]); 
				sb.append(BassLogConstant.BLOCK_DELEMETER);
			}
			boolean uriFlag = false;
			for (int i = count, devideCount = 0; i < value.length; i++, devideCount++) {
				sb.append(value[i]);
				if (uriFlag == true || (value[i] != null && value[i].equals(BassLogConstant.QUESTION_MARK))) {
					uriFlag = false;
					continue;
				}
				if(i == (value.length - 1)) break; // Last
				sb.append(devideCount % 2 == 0 ? BassLogConstant.VALUE_DELEMETER : BassLogConstant.VALUE_DELEMETER_COMMA);
				uriFlag = (value[i] != null && (value[i].equals(BassLogConstant.URI_NAME))) ? true : false;
			}
		}
		return sb.toString();
	}
	
	/**
	 * 특정 Object 의 모든것을 표시하는 메소드
	 * String[] 의 경우 모든 배열의 종류를 표시하며, List 인 경우 List 를 표시, Map 인 경우 Map 전체를 표시
	 * VO Class 인 경우 field 전체를 표시(단, field 의 경우 Public 접근제어자를 갖고있어야 함)
	 * @param sb 문자열을 입력할 StringBuffer
	 * @param value 모든것이 표시될 Object
	 * @return String Object 의 모든것이 표시된 String (not null, must be StringBuffer is not null)
	 */
	private static String convertObject(StringBuffer sb, Object value){
		boolean isFirst = true;
		
		if (value == null) {
			sb.append("Object is null");
		} else if (value instanceof String[]) {
			String[] values = (String[]) value;
			for (String str : values) {
				addCommaDelimiter(sb, isFirst);
				sb.append(str);
				isFirst = false;
			}
		} else if (value instanceof String) {
			sb.append(value);
		} else if (value instanceof Object) {
			
			if (value instanceof List<?>) {
				List<?> list = (List<?>) value;
				for (Object obj : list) {
					sb.append(obj)
					  .append(BassLogConstant.NEXT_LINE);
				}
			} else if (value instanceof Map<?,?>) {
				Map<?, ?> map = (Map<?,?>) value;
				Set<?> set = map.keySet();
				for (Object obj : set) {
					sb.append(obj)
					  .append(BassLogConstant.VALUE_DELEMETER)
					  .append(map.get(obj))
					  .append(BassLogConstant.NEXT_LINE);
				}
			} else {
				try {
					Field[] field = value.getClass().getFields();
					for (Field str : field) {
						addCommaDelimiter(sb, isFirst);
						sb.append(str.getName())
						.append(BassLogConstant.VALUE_DELEMETER)
						.append(str.get(value));
						isFirst = false;
					}
				} catch (SecurityException e) {
					logger.error(e.getMessage());
					sb.append(e);
				} catch (IllegalArgumentException e) {
					logger.error(e.getMessage());
					sb.append(e);
				} catch (IllegalAccessException e) {
					logger.error(e.getMessage());
					sb.append(e);
				} catch (Exception e) {
					logger.error(e.getMessage());
					sb.append(e);
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * Exception 의 원인 및 Stack trace 를 표시하는 method
	 * @param sb Stack trace 가 담길 StringBuffer 
	 * @param e 발생 한 Exception
	 * @return String Exception 의 원인 과 stack trace 가 담겨져있는 String (not null, must be StringBuffer is not null)
	 */
	private static String convertException(StringBuffer sb, Exception e){
		sb.append(e.getClass().getSimpleName())
		.append(BassLogConstant.STRING_DELEMETER)
		.append(e.getMessage());
		
		StackTraceElement[] stackTraceElement = e.getStackTrace();
		for (StackTraceElement element : stackTraceElement) {
			if (element != null && element.toString().startsWith(BassLogConstant.BASE_PACKAGE)) {
				sb.append(BassLogConstant.VALUE_DELEMETER_EXCEPTION)
				.append(element.toString());
			}
		}
		return sb.toString();
	}

	public static String parseException(Exception e){
		StringBuffer sb = new StringBuffer();
		sb.append(e.getClass().getSimpleName())
				.append(BassLogConstant.STRING_DELEMETER)
				.append(e.getMessage());

		StackTraceElement[] stackTraceElement = e.getStackTrace();
		for (StackTraceElement element : stackTraceElement) {
			if (element != null && element.toString().startsWith(BassLogConstant.BASE_PACKAGE)) {
				sb.append(BassLogConstant.VALUE_DELEMETER_EXCEPTION)
						.append(element.toString());
			}
		}
		return sb.toString();
	}
	
	/**
	 * 문자열의 시작임을 확인하여 comma String 을 더하는 메소드
	 * @param sb comma String 을 더하는 StringBuffer
	 * @param isFirst 문자열의 시작임을 확인하는 boolean 값
	 * @return StringBuffer comma String 가 더해지거나 더해지지 않은 StringBuffer 객체
	 */
	private static StringBuffer addCommaDelimiter(StringBuffer sb, boolean isFirst){
		if (isFirst == false) sb.append(BassLogConstant.VALUE_DELEMETER_COMMA);
		return sb;
	}
	
}
