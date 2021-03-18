package com.proyect.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class ResponseData<E> implements Serializable{
	
	private E data;
	private String message;
	private String version;
	
	
	
	public ResponseData(E data, String message, String version) {
		
		this.data = data;
		this.message = message;
		this.version = version;
	}
	public E getData() {
		return data;
	}
	public void setData(E data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	

}
