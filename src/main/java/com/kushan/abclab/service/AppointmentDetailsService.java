package com.kushan.abclab.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kushan.abclab.dao.AppointmentDetailsDao;
import com.kushan.abclab.model.AppointmentDetailsModel;

public class AppointmentDetailsService {

    public List<AppointmentDetailsModel> getAppointmentUser(HttpServletRequest request) throws ClassNotFoundException, SQLException {
        return AppointmentDetailsDao.getAppointmentUser(request);
    }
    
    public List<AppointmentDetailsModel> getAppointmentall(HttpServletRequest request) throws ClassNotFoundException, SQLException {
        return AppointmentDetailsDao.getAppointmentAll(request);
    }
    
    public boolean setStatusSer(String userappo_id, int status, String user_id, HttpServletRequest request) throws ClassNotFoundException, SQLException {
        return AppointmentDetailsDao.setStatusDao(userappo_id, status, user_id , request);
    }
}
