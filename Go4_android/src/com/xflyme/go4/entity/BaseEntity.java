package com.xflyme.go4.entity;

import java.io.Serializable;

public class BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int result;
	private String message;
	private String resptime;
	private String token;
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getResptime() {
		return resptime;
	}
	public void setResptime(String resptime) {
		this.resptime = resptime;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

}
