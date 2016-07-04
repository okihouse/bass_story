package com.oki.error.notification;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.oki.config.log.BassLogConstant;
import com.oki.config.log.BassLogManager;
import com.oki.config.vo.BassRequestDataVO;
import com.oki.util.json.JsonUtils;

import net.gpedro.integrations.slack.SlackApi;
import net.gpedro.integrations.slack.SlackAttachment;
import net.gpedro.integrations.slack.SlackField;
import net.gpedro.integrations.slack.SlackMessage;

@Service
public class BassErrorNotificateSlack {

	@Autowired
	private BassRequestDataVO bassRequestDataVO;
	
	@Value("${slack.notification.url}") 
    private String url;
	
	@PostConstruct
    public void init() {
        slackApi = new SlackApi(url);
    }
	
	private SlackApi slackApi;
	
	@Async
	public void send(Exception e){
		List<SlackField> fields = new ArrayList<SlackField>();

		fields.add(setField("error message", e.getMessage(), false));
		fields.add(setField("error stacktrace", BassLogManager.parseException(e), false));
		fields.add(setField("request uri", bassRequestDataVO.getMethod() + " : " + bassRequestDataVO.getRequestURI(), false));
		fields.add(setField("request param", JsonUtils.toPrettyJson(JsonUtils.toJson(bassRequestDataVO.getParameterMaps())), false));
		fields.add(setField("request time", convertDateString(), false));
		fields.add(setField("request tid", bassRequestDataVO.getTid(), false));
		fields.add(setHeaderField("request header", bassRequestDataVO.getRequest(), false));
		fields.add(setField("host ip", BassLogConstant.IP_ADDRESS, false));

		send(fields, e);
	}

	@Async
	public void send(Exception e, Object obj){
		List<SlackField> fields = new ArrayList<SlackField>();
		
		fields.add(setField("error message", e.getMessage(), false));
		fields.add(setField("error stacktrace", BassLogManager.parseException(e), false));
		fields.add(setField("request uri", bassRequestDataVO.getMethod() + " : " + bassRequestDataVO.getRequestURI(), false));
		fields.add(setField("request param", JsonUtils.toPrettyJson(JsonUtils.toJson(bassRequestDataVO.getParameterMaps())), false));
		fields.add(setField("request body", JsonUtils.toPrettyJson(JsonUtils.toJson(obj)), false));
		fields.add(setField("request time", convertDateString(), false));
		fields.add(setField("request tid", bassRequestDataVO.getTid(), false));
		fields.add(setHeaderField("request header", bassRequestDataVO.getRequest(), false));
		fields.add(setField("host ip", BassLogConstant.IP_ADDRESS, false));

		send(fields, e);
	}
	
	@Async
	public void send(Exception e, String body){
		List<SlackField> fields = new ArrayList<SlackField>();
		
		fields.add(setField("error message", e.getMessage(), false));
		fields.add(setField("error stacktrace", BassLogManager.parseException(e), false));
		fields.add(setField("request uri", bassRequestDataVO.getMethod() + " : " + bassRequestDataVO.getRequestURI(), false));
		fields.add(setField("request param", JsonUtils.toPrettyJson(JsonUtils.toJson(bassRequestDataVO.getParameterMaps())), false));
		fields.add(setField("request body", body, false));
		fields.add(setField("request time", convertDateString(), false));
		fields.add(setField("request tid", bassRequestDataVO.getTid(), false));
		fields.add(setHeaderField("request header", bassRequestDataVO.getRequest(), false));
		fields.add(setField("host ip", BassLogConstant.IP_ADDRESS, false));
		
		send(fields, e);
	}

	private void send(List<SlackField> slackFields, Exception e){
		SlackAttachment slackAttachment = new SlackAttachment();
		slackAttachment.setFallback("Error");
		slackAttachment.setColor("danger");
		slackAttachment.setFields(slackFields);
		slackAttachment.setTitle(e.getMessage());
		slackAttachment.setText(BassLogManager.parseException(e));
		
		SlackMessage slackMessage = new SlackMessage("");
		slackMessage.setIcon(":exclamation:");
		slackMessage.setAttachments(Collections.singletonList(slackAttachment));
		
		slackApi.call(slackMessage);
	}

	private SlackField setField(String title, String value, boolean shorten) {
		SlackField slackField = new SlackField();
		slackField.setTitle(title);
		slackField.setValue(value);
		slackField.setShorten(shorten);
		return slackField;
	}

	private SlackField setHeaderField(String title, HttpServletRequest request, boolean shorten) {
		Map<String, String> headerMap = new HashMap<String, String>();

		Enumeration<String> enumeration = request.getHeaderNames();
		while (enumeration.hasMoreElements()) {
			String name = enumeration.nextElement();
			headerMap.put(name, request.getHeader(name));
		}

		SlackField slackField = new SlackField();
		slackField.setTitle(title);
		slackField.setShorten(shorten);
		slackField.setValue(JsonUtils.toPrettyJson(JsonUtils.toJson(headerMap)));
		return slackField;
	}

	private final SimpleDateFormat simpleDateFormats = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private String convertDateString() {
		return simpleDateFormats.format(new Date(System.currentTimeMillis()));
	}
	
}
