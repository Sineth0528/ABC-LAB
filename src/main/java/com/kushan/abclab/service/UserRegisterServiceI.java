package com.kushan.abclab.service;

import java.sql.SQLException;

import com.kushan.abclab.model.UserRegisterModel;

public interface UserRegisterServiceI {

    public boolean insertNewUser(UserRegisterModel UserRegisterModel) throws ClassNotFoundException, SQLException;

}
