package com.kushan.abclab.service;

import java.sql.SQLException;

import com.kushan.abclab.dao.UserRegisterDao;
import com.kushan.abclab.model.UserRegisterModel;

public class UserRegisterSer implements UserRegisterServiceI {
	
	private static UserRegisterSer userServiceObj;
	
	public UserRegisterSer() {
	}
	
	public static synchronized UserRegisterSer getProductServiceInstance() {
		
		if(userServiceObj == null) {
			userServiceObj = new UserRegisterSer();
		}
		
		return userServiceObj;
	}
	
	private UserRegisterDao userRegisterManager() {
		return new UserRegisterDao();
	}
	
    public boolean insertNewUser(UserRegisterModel userregistermodel) throws ClassNotFoundException, SQLException {
        return userRegisterManager().insertUserDetails(userregistermodel);
    }
}
