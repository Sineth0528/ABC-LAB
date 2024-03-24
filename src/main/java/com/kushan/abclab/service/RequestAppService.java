package com.kushan.abclab.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kushan.abclab.dao.ReqAppDao;
import com.kushan.abclab.model.AppointmentInfo;
import com.kushan.abclab.model.ReqAppModel;

public class RequestAppService {

    private static RequestAppService instance;
    private ReqAppDao reqAppDao;

    private RequestAppService() {
        this.reqAppDao = new ReqAppDao();
    }

    public static synchronized RequestAppService getInstance() {
        if (instance == null) {
            instance = new RequestAppService();
        }
        return instance;
    }

    public List<ReqAppModel> GetTypeName() throws SQLException, ClassNotFoundException {
        return reqAppDao.getAllAppointName();
    }

    public AppointmentInfo getAvilableDateSer(String appointmentId, String selectedDate) throws SQLException, ClassNotFoundException {
        return reqAppDao.CheckAvilabaleDate(appointmentId, selectedDate);
    }

    public boolean insertReqApp(ReqAppModel reqAppModel, HttpServletRequest request) throws ClassNotFoundException, SQLException {
        return reqAppDao.insertNewReq(reqAppModel, request);
    }

}
