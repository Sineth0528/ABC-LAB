package com.kushan.abclab.model;

public class ReqAppModel {
	
	private int id;
	private String label;
	private String dropID;
	
	private String date;
    private Double price;
    
    private String appid;
    private String time;
    private String email;
    
    public ReqAppModel() {

	}
    
    public ReqAppModel(int id ,String date, Double price) {
    	this.id = id;
    	this.date = date;
        this.price = price;
    }
    

    public ReqAppModel(int id, String label) {
		this.id = id;
		this.label = label;
	}

    public void AppointmentInfo(String date, Double price) {
        this.date = date;
        this.price = price;
    }
    
    
    public String getDate() {
        return date;
    }

    public Double getPrice() {
        return price;
    }
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}


	public String getDropID() {
		return dropID;
	}


	public void setDropID(String dropID) {
		this.dropID = dropID;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	

}
