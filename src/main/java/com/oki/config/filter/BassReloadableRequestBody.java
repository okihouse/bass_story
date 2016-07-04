package com.oki.config.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;

public class BassReloadableRequestBody extends HttpServletRequestWrapper {

	private ByteArrayOutputStream byteArrayOutputStream;
	
	public BassReloadableRequestBody(HttpServletRequest request) {
		super(request);
	}
	
	@Override
	public ServletInputStream getInputStream() throws IOException {
		if (byteArrayOutputStream == null) configureInputStream();
		return new BassReloadableServletInputStream();
	}
	
	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}
	
	private void configureInputStream() throws IOException {
		byteArrayOutputStream = new ByteArrayOutputStream();
		IOUtils.copy(super.getInputStream(), byteArrayOutputStream);
	}

	
	private class BassReloadableServletInputStream extends ServletInputStream {
		private InputStream inputStream;
		
		public BassReloadableServletInputStream() {
			inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		}
		
		@Override
		public int read() throws IOException {
			return inputStream.read();
		}

		@Override
		public boolean isFinished() {
			return false;
		}

		@Override
		public boolean isReady() {
			return false;
		}

		@Override
		public void setReadListener(ReadListener listener) {
			// Nothing to do
		}
	}

}
