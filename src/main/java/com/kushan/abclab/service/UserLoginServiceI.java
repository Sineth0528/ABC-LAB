package com.kushan.abclab.service;

import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import com.kushan.abclab.model.UserLoginModel;

public interface UserLoginServiceI {
	
	 public boolean checkLogin(UserLoginModel UserLoginModel, HttpServletRequest request) throws ClassNotFoundException, SQLException;


}
