package com.kushan.abclab.model;

public class AppointmentDetailsModel {

	private String userappo_id;
	private String appo_name;
	private String date;
	private String time;
	private int status;
	private String price;
	private String userId;
	
	public String getUserappo_id() {
		return userappo_id;
	}
	public void setUserappo_id(String userappo_id) {
		this.userappo_id = userappo_id;
	}
	public String getAppo_name() {
		return appo_name;
	}
	public void setAppo_name(String appo_name) {
		this.appo_name = appo_name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	
	
}
